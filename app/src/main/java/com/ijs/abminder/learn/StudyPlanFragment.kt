package com.ijs.abminder.learn

import android.Manifest
import android.annotation.*
import android.app.*
import android.content.*
import android.content.pm.*
import android.os.*
import android.view.*
import android.widget.*
import androidx.annotation.*
import androidx.appcompat.app.AlertDialog
import androidx.core.app.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.*
import androidx.transition.AutoTransition
import androidx.transition.TransitionManager
import com.google.gson.*
import com.ijs.abminder.*
import java.util.*

class PersonalizedStudyPlanFragment : Fragment() {

    private lateinit var studyPlanRecyclerView : RecyclerView
    private lateinit var studyPlanAdapter : StudyPlanAdapter
    private val studyPlans = mutableListOf<StudyPlan>()

    private val NOTIFICATION_CHANNEL_ID = "study_plan_channel"
    private val NOTIFICATION_CHANNEL_NAME = "Study Plan Notification"
    private val NOTIFICATION_PERMISSION_REQUEST_CODE = 1

    override fun onCreateView(
        inflater : LayoutInflater,
        container : ViewGroup?,
        savedInstanceState : Bundle?,
    ) : View? {
        TransitionManager.beginDelayedTransition(container!!, AutoTransition())
        return inflater.inflate(R.layout.fragment_study_plan, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onViewCreated(view : View, savedInstanceState : Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        createNotificationChannel()

        studyPlanRecyclerView = view.findViewById(R.id.study_plan_recycler_view)
        studyPlanRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        studyPlanAdapter = StudyPlanAdapter(studyPlans) { studyPlan ->
            deleteStudyPlan(studyPlan)
        }
        studyPlanRecyclerView.adapter = studyPlanAdapter

        loadStudyPlansFromDataSource()
        loadStudyPlansFromSharedPreferences()


        view.findViewById<Button>(R.id.add_study_plan_button).setOnClickListener {
            showAddStudyPlanDialog()
        }
    }

    private fun createNotificationChannel() {
        val channel = NotificationChannel(
            NOTIFICATION_CHANNEL_ID,
            NOTIFICATION_CHANNEL_NAME,
            NotificationManager.IMPORTANCE_DEFAULT
        )
        val manager = requireContext().getSystemService(NotificationManager::class.java)
        manager.createNotificationChannel(channel)
    }

    private fun deleteStudyPlan(studyPlan : StudyPlan) {
        val index = studyPlans.indexOf(studyPlan)
        if (index != -1) {
            studyPlans.removeAt(index)
            studyPlanAdapter.notifyItemRemoved(index)
            saveStudyPlansToSharedPreferences()

            // Cancel the scheduled notificationFLAG_UPDATE_CURRENT
            val notificationIntent = Intent(requireContext(), AlarmReceiver::class.java)
            val pendingIntent = PendingIntent.getBroadcast(
                requireContext(),
                studyPlan.hashCode(),
                notificationIntent,
                PendingIntent.FLAG_IMMUTABLE
            )
            val alarmManager =
                requireContext().getSystemService(Context.ALARM_SERVICE) as AlarmManager
            alarmManager.cancel(pendingIntent)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun loadStudyPlansFromDataSource() {
        studyPlans.addAll(
            listOf(
                StudyPlan("Sample Study Plan", "09:00", "11:00"),
            )
        )
        studyPlanAdapter.notifyDataSetChanged()
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    @SuppressLint("MissingInflatedId", "ScheduleExactAlarm")
    private fun showAddStudyPlanDialog() {
        val dialogView =
            LayoutInflater.from(requireContext()).inflate(R.layout.dialog_add_study_plan, null)
        val titleEditText = dialogView.findViewById<EditText>(R.id.study_plan_title_edit_text)
        val startTimeTextView = dialogView.findViewById<TextView>(R.id.start_time_text_view)
        val endTimeTextView = dialogView.findViewById<TextView>(R.id.end_time_text_view)
        val datePicker = dialogView.findViewById<DatePicker>(R.id.date_picker)

        var startHour = 0
        var startMinute = 0
        var endHour = 0
        var endMinute = 0
        var selectedDate : Calendar? = null

        startTimeTextView.setOnClickListener {
            val currentTime = Calendar.getInstance()
            val startTimePickerDialog = TimePickerDialog(
                requireContext(), { _, hourOfDay, minute ->
                    startHour = hourOfDay
                    startMinute = minute
                    val startTime = "%02d:%02d".format(startHour, startMinute)
                    startTimeTextView.text = startTime
                }, currentTime.get(Calendar.HOUR_OF_DAY), currentTime.get(Calendar.MINUTE), false
            )
            startTimePickerDialog.show()
        }

        endTimeTextView.setOnClickListener {
            val currentTime = Calendar.getInstance()
            val endTimePickerDialog = TimePickerDialog(
                requireContext(), { _, hourOfDay, minute ->
                    endHour = hourOfDay
                    endMinute = minute
                    val endTime = "%02d:%02d".format(endHour, endMinute)
                    endTimeTextView.text = endTime
                }, currentTime.get(Calendar.HOUR_OF_DAY), currentTime.get(Calendar.MINUTE), false
            )
            endTimePickerDialog.show()
        }

        datePicker.setOnDateChangedListener { _, year, monthOfYear, dayOfMonth ->
            val calendar = Calendar.getInstance()
            calendar.set(year, monthOfYear, dayOfMonth)
            selectedDate = calendar
        }

        val alertDialog = AlertDialog.Builder(requireContext()).setView(dialogView)
            .setPositiveButton("Save") { _, _ ->
                val title = titleEditText.text.toString().trim()

                if (title.isNotEmpty() && selectedDate != null && startHour != 0 && startMinute != 0 && endHour != 0 && endMinute != 0) {
                    val startTime = "%02d:%02d".format(startHour, startMinute)
                    val endTime = "%02d:%02d".format(endHour, endMinute)
                    val newStudyPlan = StudyPlan(title, startTime, endTime)
                    studyPlans.add(newStudyPlan)
                    studyPlanAdapter.notifyItemInserted(studyPlans.size - 1)
                    saveStudyPlansToSharedPreferences()

                    // Schedule the notification
                    val notificationIntent = Intent(requireContext(), AlarmReceiver::class.java)
                    notificationIntent.putExtra("study_plan_title", title)

                    val pendingIntent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                        PendingIntent.getBroadcast(
                            requireContext(),
                            newStudyPlan.hashCode(),
                            notificationIntent,
                            PendingIntent.FLAG_IMMUTABLE
                        )
                    } else {
                        PendingIntent.getBroadcast(
                            requireContext(),
                            newStudyPlan.hashCode(),
                            notificationIntent,
                            PendingIntent.FLAG_UPDATE_CURRENT
                        )
                    }

                    val calendar = selectedDate!!.clone() as Calendar
                    calendar.set(Calendar.HOUR_OF_DAY, startHour)
                    calendar.set(Calendar.MINUTE, startMinute)
                    calendar.set(Calendar.SECOND, 0)
                    calendar.set(Calendar.MILLISECOND, 0)

                    val alarmTime = calendar.timeInMillis

                    val alarmManager =
                        requireContext().getSystemService(Context.ALARM_SERVICE) as AlarmManager

                    alarmManager.setExactAndAllowWhileIdle(
                        AlarmManager.RTC_WAKEUP, alarmTime, pendingIntent
                    )

                    // Request the notification permission if needed
                    if (ActivityCompat.checkSelfPermission(
                            requireContext(), Manifest.permission.POST_NOTIFICATIONS
                        ) != PackageManager.PERMISSION_GRANTED
                    ) {
                        ActivityCompat.requestPermissions(
                            requireActivity(),
                            arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                            NOTIFICATION_PERMISSION_REQUEST_CODE
                        )
                    }
                } else {
                    Toast.makeText(
                        requireContext(), "Please fill in all fields", Toast.LENGTH_SHORT
                    ).show()
                }
            }.setNegativeButton("Cancel", null).setCancelable(false).create()
        alertDialog.show()
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun showNotification(title : String, startTime : String, endTime : String) {
        if (ActivityCompat.checkSelfPermission(
                requireContext(), Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                NOTIFICATION_PERMISSION_REQUEST_CODE
            )
            return
        }

        val notification = NotificationCompat.Builder(requireContext(), NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_logo_modified).setContentTitle("Study Plan Reminder")
            .setContentText("$title: $startTime - $endTime")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT).build()

        val notificationManager = NotificationManagerCompat.from(requireContext())
        notificationManager.notify(System.currentTimeMillis().toInt(), notification)
    }

    @SuppressLint("InflateParams")
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    @Deprecated("Deprecated in Java")
    override fun onRequestPermissionsResult(
        requestCode : Int,
        permissions : Array<out String>,
        grantResults : IntArray,
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == NOTIFICATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, you can show the notification

                val dialogView = LayoutInflater.from(requireContext())
                    .inflate(R.layout.dialog_add_study_plan, null)
                val startTimeTextView = dialogView.findViewById<TextView>(R.id.start_time_text_view)
                val endTimeTextView = dialogView.findViewById<TextView>(R.id.end_time_text_view)

                var startHour = 0
                var startMinute = 0
                var endHour = 0
                var endMinute = 0

                startTimeTextView.setOnClickListener {
                    val currentTime = Calendar.getInstance()
                    val startTimePickerDialog = TimePickerDialog(
                        requireContext(),
                        { _, hourOfDay, minute ->
                            startHour = hourOfDay
                            startMinute = minute
                            val startTime = "%02d:%02d".format(startHour, startMinute)
                            startTimeTextView.text = startTime
                        },
                        currentTime.get(Calendar.HOUR_OF_DAY),
                        currentTime.get(Calendar.MINUTE),
                        false
                    )
                    startTimePickerDialog.show()
                }

                endTimeTextView.setOnClickListener {
                    val currentTime = Calendar.getInstance()
                    val endTimePickerDialog = TimePickerDialog(
                        requireContext(),
                        { _, hourOfDay, minute ->
                            endHour = hourOfDay
                            endMinute = minute
                            val endTime = "%02d:%02d".format(endHour, endMinute)
                            endTimeTextView.text = endTime
                        },
                        currentTime.get(Calendar.HOUR_OF_DAY),
                        currentTime.get(Calendar.MINUTE),
                        false
                    )
                    endTimePickerDialog.show()
                }
                val title =
                    view?.findViewById<TextView>(R.id.study_plan_title_edit_text)?.text.toString()
                        .trim()
                val startTime = "%02d:%02d".format(startHour, startMinute)
                val endTime = "%02d:%02d".format(endHour, endMinute)
                showNotification(title, startTime, endTime)
            } else {
                // Permission denied, handle accordingly
                Toast.makeText(
                    requireContext(), "Permission denied to show notifications", Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun saveStudyPlansToSharedPreferences() {
        val sharedPreferences =
            requireContext().getSharedPreferences("study_plans", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val studyPlanDataList = studyPlans.map { StudyPlan(it.title, it.startTime, it.endTime) }
        val jsonString = Gson().toJson(studyPlanDataList)
        editor.putString("study_plans_data", jsonString)
        editor.apply()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun loadStudyPlansFromSharedPreferences() {
        val sharedPreferences =
            requireContext().getSharedPreferences("study_plans", Context.MODE_PRIVATE)
        val jsonString = sharedPreferences.getString("study_plans_data", null)
        if (jsonString != null) {
            val studyPlanDataList =
                Gson().fromJson(jsonString, Array<StudyPlan>::class.java).toList()
            studyPlans.clear()
            studyPlans.addAll(studyPlanDataList.map {
                StudyPlan(
                    it.title, it.startTime, it.endTime
                )
            })
            studyPlanAdapter.notifyDataSetChanged()
        }
    }

    override fun onDestroy() {
        val mainActivity = requireActivity() as MainActivity
        mainActivity.toolbar.title = getString(R.string.learn)
        super.onDestroy()
    }

    override fun onDetach() {
        super.onDetach()
        val mainActivity = requireActivity() as MainActivity
        mainActivity.toolbar.title = getString(R.string.learn)
    }
}