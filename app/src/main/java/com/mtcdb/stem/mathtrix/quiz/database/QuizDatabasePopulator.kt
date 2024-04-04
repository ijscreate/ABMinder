package com.mtcdb.stem.mathtrix.quiz.database

open class QuizDataPopulator(private val dbHelper : QuizDatabaseHelper) {

    fun populateQuizData() {

        populateEasyQuestions()
        populateEasyQuestionsBUSINESS()
        populateEasyQuestionsFABM()
        populateEasyQuestionsECONOMICS()
        populateMediumQuestions()
        populateMediumQuestionsBUSINESS()
        populateMediumQuestionsFABM()
        populateMediumQuestionsECONOMICS()
        populateHardQuestions()
        populateHardQuestionsBUSINESS()
        populateHardQuestionsFABM()
        populateHardQuestionsECONOMICS()
    }

    private fun insertQuizData(
        subject : String,
        question : String,
        options : String,
        correctAnswerIndex : Int,
        difficultyLevel : String,
    ) {
        dbHelper.insertQuizData(subject, question, options, correctAnswerIndex, difficultyLevel)
    }

    private fun populateEasyQuestions() {

        insertQuizData(
            "Business Mathematics",
            "What is the formula for calculating gross profit?",
            "A. Revenue - Cost of Goods Sold, B. Revenue / Cost of Goods Sold, C. Revenue + Cost of Goods Sold, D. Revenue x Cost of Goods Sold",
            0, // Index of correct answer (A)
            "Easy"
        )

        insertQuizData(
            "Business Mathematics",
            "What is the formula for calculating net profit?",
            "A. Gross Profit + Expenses, B. Gross Profit / Expenses, C. Gross Profit - Expenses, D. Gross Profit x Expenses",
            2, // Index of correct answer (C)
            "Easy"
        )

        insertQuizData(
            "Business Mathematics",
            "What is the formula for calculating return on investment (ROI)?",
            "A. (Net Profit / Cost of Investment) x 100, B. (Gross Profit / Cost of Investment) x 100, C. (Revenue / Cost of Investment) x 100, D. (Expenses / Cost of Investment) x 100",
            0, // Index of correct answer (A)
            "Easy"
        )

        insertQuizData(
            "Business Mathematics",
            "What is the formula for calculating contribution margin?",
            "A. (Fixed Costs / Revenue) x 100, B. (Revenue - Variable Costs) / Revenue, C. (Variable Costs / Revenue) x 100, D. (Revenue - Fixed Costs) / Revenue",
            1, // Index of correct answer (B)
            "Easy"
        )

        insertQuizData(
            "Business Mathematics",
            "What is the formula to calculate simple interest?",
            "A. PRT, B. P/R, C. PT, D. PR/T",
            0, // Index of correct answer (A)
            "Easy"
        )

        insertQuizData(
            "Business Mathematics",
            "Define 'Profit Margin'.",
            "A. The total revenue minus total expenses, B. The percentage of revenue that represents profit, C. The difference between selling price and cost price, D. The total profit divided by the number of units sold",
            1, // Index of correct answer (B)
            "Easy"
        )

        insertQuizData(
            "Business Mathematics",
            "What does the term 'Break-even Point' represent in business?",
            "A. The point where total revenue equals total expenses, B. The point where total revenue exceeds total expenses, C. The point where total expenses are minimized, D. The point where total profit is maximized",
            0, // Index of correct answer (A)
            "Easy"
        )

        insertQuizData(
            "Business Mathematics",
            "Define 'Depreciation'.",
            "A. The process of increasing the value of an asset, B. The process of calculating the value of a liability, C. The process of allocating the cost of an asset over its useful life, D. The process of reducing the value of an asset over time",
            3, // Index of correct answer (D)
            "Easy"
        )

        insertQuizData(
            "Business Mathematics",
            "What is the formula for calculating the break-even point in units?",
            "A. Fixed Costs / (Selling Price per Unit - Variable Costs per Unit), B. Fixed Costs / Selling Price per Unit, C. Variable Costs per Unit / (Selling Price per Unit - Variable Costs per Unit), D. Selling Price per Unit / Variable Costs per Unit",
            0, // Index of correct answer (A)
            "Easy"
        )

        insertQuizData(
            "Business Mathematics",
            "What is the average of a set of numbers called?",
            "A. Median, B. Mean, C. Mode, D. Range",
            0,
            "Easy"
        )
        insertQuizData(
            "Business Mathematics",
            "A percentage rate used to express the cost of borrowing money is called the:",
            "A. Interest rate, B. Markup rate, C. Discount rate, D. All of the above",
            0,
            "Easy"
        )
        insertQuizData(
            "Business Mathematics",
            "Subtracting a discount from the retail price of an item gives you the:",
            "A. Wholesale price, B. Net price, C. Profit margin, D. Selling price",
            0,
            "Easy"
        )

        insertQuizData(
            "Business Mathematics",
            "Which of the following decimal values is the smallest?",
            "0.0001, 0.001, 0.01, 0.1",
            0,
            "Easy"
        )

        insertQuizData(
            "Business Mathematics",
            "What is the value of 1.5% as a decimal?",
            "0.015, 0.01, 0.15, 0.1",
            0,
            "Easy"
        )

        insertQuizData(
            "Business Mathematics",
            "What is the ratio of 15 and 20 in simplest form?",
            "15/20, 3/4, 2/3, 4/5",
            1,
            "Easy"
        )
    }

    private fun populateMediumQuestions() {
        // Add medium Business Mathematics quiz questions here

        insertQuizData(
            "Business Mathematics",
            "Which of the following operations is NOT closed for fractions?",
            "Addition, Subtraction, Multiplication, Division",
            3,
            "Medium"
        )

        insertQuizData(
            "Business Mathematics",
            "What is the purpose of using ratios in financial analysis?",
            "A. To compare different companies, B. To calculate total expenses, C. To determine liquidity ratios, D. To forecast stock prices",
            0, // Index of correct answer (A)
            "Medium"
        )

        insertQuizData(
            "Business Mathematics",
            "What does the term 'net profit margin' indicate about a business?",
            "A. The total revenue generated, B. The profit after all expenses are deducted, C. The cost of goods sold, D. The gross profit before deductions",
            1, // Index of correct answer (B)
            "Medium"
        )

        insertQuizData(
            "Business Mathematics",
            "What formula is used to calculate compound interest?",
            "A. P(1 + r/n)^(nt), B. P / r / t, C. P + r + t, D. P - r - t",
            0, // Index of correct answer (A)
            "Medium"
        )

        insertQuizData(
            "Business Mathematics",
            "Why is it important for businesses to understand the concept of mark-up?",
            "A. To calculate discounts, B. To determine selling prices, C. To assess profit margins, D. To forecast sales revenue",
            2, // Index of correct answer (C)
            "Medium"
        )

        insertQuizData(
            "Business Mathematics",
            "How are measures of central tendency useful in analyzing business data?",
            "A. To identify outliers in the data, B. To calculate standard deviation, C. To determine the average or typical value, D. To plot scatter plots",
            2, // Index of correct answer (C)
            "Medium"
        )

        insertQuizData(
            "Business Mathematics",
            "What role does the concept of ratio and proportion play in pricing strategies?",
            "A. To calculate discounts, B. To set competitive prices, C. To determine profit margins, D. To forecast demand",
            1, // Index of correct answer (B)
            "Medium"
        )

        insertQuizData(
            "Business Mathematics",
            "How can businesses use percentages to analyze financial performance?",
            "A. To compare revenue over time, B. To calculate total assets, C. To assess profitability ratios, D. To forecast future expenses",
            0, // Index of correct answer (A)
            "Medium"
        )

        insertQuizData(
            "Business Mathematics",
            "What is the significance of understanding simple interest in financial transactions?",
            "A. To calculate compound interest, B. To assess borrowing costs, C. To determine investment returns, D. To forecast market trends",
            2, // Index of correct answer (C)
            "Medium"
        )

        insertQuizData(
            "Business Mathematics",
            "How do businesses use proportions in analyzing sales data?",
            "A. To calculate profit margins, B. To compare sales figures, C. To forecast future sales, D. To determine inventory turnover",
            3, // Index of correct answer (D)
            "Medium"
        )

        insertQuizData(
            "Business Mathematics",
            "Why is it important for businesses to understand the concept of gross profit?",
            "A. To calculate revenue, B. To assess profitability, C. To determine operating expenses, D. To forecast market trends",
            1, // Index of correct answer (B)
            "Medium"
        )

        insertQuizData(
            "Business Mathematics",
            "What role does ratio and proportion play in inventory management?",
            "A. To calculate inventory turnover ratio, B. To determine reorder points, C. To assess carrying costs, D. To forecast demand trends",
            0, // Index of correct answer (A)
            "Medium"
        )

        insertQuizData(
            "Business Mathematics",
            "How can businesses use percentages to analyze pricing strategies?",
            "A. To set competitive prices, B. To calculate total revenue, C. To assess profit margins, D. To forecast market demand",
            2, // Index of correct answer (C)
            "Medium"
        )

        insertQuizData(
            "Business Mathematics",
            "What formula is used to calculate net earnings?",
            "A. Revenue - Expenses, B. Revenue / Expenses, C. Revenue + Expenses, D. Revenue x Expenses",
            0, // Index of correct answer (A)
            "Medium"
        )

        insertQuizData(
            "Business Mathematics",
            "Why is it important for businesses to understand the concept of discounts?",
            "A. To calculate profit margins, B. To attract customers, C. To assess operating costs, D. To forecast sales revenue",
            1, // Index of correct answer (B)
            "Medium"
        )

        insertQuizData(
            "Business Mathematics",
            "How can businesses use proportions in analyzing market share?",
            "A. To calculate market growth rate, B. To compare market shares, C. To forecast future sales, D. To determine pricing strategies",
            1, // Index of correct answer (B)
            "Medium"
        )

        insertQuizData(
            "Business Mathematics",
            "What role does percentage play in financial reporting?",
            "A. To compare financial statements over time, B. To calculate total assets, C. To assess liquidity ratios, D. To forecast market trends",
            0, // Index of correct answer (A)
            "Medium"
        )

        insertQuizData(
            "Business Mathematics",
            "How do businesses use ratios in analyzing profitability?",
            "A. To calculate revenue growth rate, B. To determine profit margins, C. To assess cost structures, D. To forecast market demand",
            2, // Index of correct answer (C)
            "Medium"
        )

        insertQuizData(
            "Business Mathematics",
            "What is the formula for calculating gross margin?",
            "A. (Revenue - Cost of Goods Sold) / Revenue, B.(Revenue / Cost of Goods Sold) x 100%, C.(Revenue - Cost of Goods Sold) x 100%, D.(Revenue / Cost of Goods Sold)",
            1, // Index of correct answer (B)
            "Medium"
        )

        insertQuizData(
            "Business Mathematics",
            "Why is it important for businesses to understand the concept of compound interest?",
            "A.To calculate simple interest,B.To assess borrowing costs,C.To determine investment returns,D.To forecast market trends",
            3,// Index of correct answer (D)
            "Medium"
        )

        insertQuizData(
            "Business Mathematics",
            "What is the significance of understanding mark-up in pricing strategies?",
            "A.To calculate discounts,B.To set competitive prices,C.To assess profit margins,D.To forecast sales revenue",
            2,// Index of correct answer (C)
            "Medium"
        )

        insertQuizData(
            "Business Mathematics",
            "What is 'Net Income' in business?",
            "A. The total revenue generated by a business, B. The amount of money invested in a business, C. The profit earned after deducting all expenses from revenue, D. The cost price of a product",
            2, // Index of correct answer (C)
            "Medium"
        )

        insertQuizData(
            "Business Mathematics",
            "Define 'Amortization' in business.",
            "A. The process of increasing the value of an asset, B. The process of decreasing the value of an asset over time, C. The cost of borrowing money, D. The process of paying off debt over a period of time",
            3, // Index of correct answer (D)
            "Medium"
        )

        insertQuizData(
            "Business Mathematics",
            "What is 'Equity' in business?",
            "A. The percentage of profit earned on a product or service after deducting all costs, B. The amount of money invested in a business, C. The value of an asset after deducting liabilities, D. The total revenue generated by a business",
            2, // Index of correct answer (C)
            "Medium"
        )

        insertQuizData(
            "Business Mathematics",
            "Define 'Operating Expenses' in business.",
            "A. The total revenue generated by a business, B. The costs incurred in the day-to-day operations of a business, C. The percentage of profit earned on a product or service, D. The cost price of a product",
            1, // Index of correct answer (B)
            "Medium"
        )

        insertQuizData(
            "Business Mathematics",
            "What is 'Revenue' in business?",
            "A. The percentage of profit earned on a product or service after deducting all costs, B. The total expenses incurred by a business, C. The total amount of money earned from sales of goods or services, D. The cost price of a product",
            2, // Index of correct answer (C)
            "Medium"
        )

        insertQuizData(
            "Business Mathematics",
            "Define 'Gross Profit' in business.",
            "A. The total revenue generated by a business, B. The profit earned before deducting expenses, C. The percentage of profit earned on a product or service, D. The cost price of a product",
            1, // Index of correct answer (B)
            "Medium"
        )

        insertQuizData(
            "Business Mathematics",
            "What is 'Inventory Turnover Ratio' in business?",
            "A. The percentage of profit earned on a product or service after deducting all costs, B. The ratio of cost of goods sold to average inventory, C. The total revenue generated by a business, D. The cost price of a product",
            1, // Index of correct answer (B)
            "Medium"
        )

        insertQuizData(
            "Business Mathematics",
            "Define 'Operating Income' in business.",
            "A. The total revenue generated by a business, B. The income earned from investments, C. The profit earned from core business operations before interest and taxes, D. The cost price of a product",
            2, // Index of correct answer (C)
            "Medium"
        )

        insertQuizData(
            "Business Mathematics",
            "What is 'Return on Investment (ROI)' in business?",
            "A. The total revenue generated by a business, B. The percentage of profit earned on a product or service after deducting all costs, C. A measure of the profitability of an investment relative to its cost, D. The cost price of a product",
            2, // Index of correct answer (C)
            "Medium"
        )


    }

    private fun populateHardQuestions() {
        // Add hard Business Mathematics quiz questions here

        insertQuizData(
            "Business Mathematics",
            "If two numbers are in proportion, and the first number is 8, what is the second number if the third number is 12 and the fourth number is 18?",
            "9, 12, 14, 16",
            1,
            "Hard"
        )
        insertQuizData(
            "Business Mathematics",
            "A cellphone cover sells for ₱250 and costs ₱185 to manufacture. What is the margin in percentage?",
            "A. 0%, B. 0%, C. 26%, D. 35%",
            2, // Index of correct answer (C)
            "Hard"
        )

        insertQuizData(
            "Business Mathematics",
            "A seller wants a mark-up based on cost of 12%. If a tumbler costs ₱460, how much will be the mark-up?",
            "A. ₱5, B. ₱55, C. ₱465, D. ₱515",
            3, // Index of correct answer (D)
            "Hard"
        )

        insertQuizData(
            "Business Mathematics",
            "Claire sold her craft at a selling price of ₱750. The cost of her craft is ₱475. What is the mark-up percentage?",
            "A. 0%, B. 0%, C. 37%, D. 58%",
            2, // Index of correct answer (C)
            "Hard"
        )

        insertQuizData(
            "Business Mathematics",
            "A company's total revenue is ₱500,000 and its total costs are ₱350,000. Calculate the company's profit margin in percentage.",
            "A. 30%, B. 40%, C. 50%, D. 60%",
            1, // Index of correct answer (B)
            "Hard"
        )

        insertQuizData(
            "Business Mathematics",
            "If a company's fixed costs are ₱100,000, its variable costs are ₱50 per unit, and it sells each unit for ₱100, how many units does it need to sell to break even?",
            "A. 1,000 units, B. 2,000 units, C. 3,000 units, D. 4,000 units",
            2, // Index of correct answer (C)
            "Hard"
        )

        insertQuizData(
            "Business Mathematics",
            "A store bought 100 units of a product for ₱10 each and sold them for ₱15 each. What is the store's total profit?",
            "A. ₱500, B. ₱1,000, C. ₱1,500, D. ₱2,000",
            2, // Index of correct answer (C)
            "Hard"
        )

        insertQuizData(
            "Business Mathematics",
            "If a company's revenue is ₱1,000,000 and its total costs are ₱800,000, what is the company's profit margin in percentage?",
            "A. 10%, B. 20%, C. 30%, D. 40%",
            2, // Index of correct answer (C)
            "Hard"
        )

        insertQuizData(
            "Business Mathematics",
            "A company's initial investment is ₱50,000. If the company's profit is ₱20,000 per month, how many months will it take to recover the initial investment?",
            "A. 2.5 months, B. 5 months, C. 7.5 months, D. 10 months",
            3, // Index of correct answer (D)
            "Hard"
        )


        insertQuizData(
            "Business Mathematics",
            "A company's total revenue is ₱800,000 and its total variable costs are ₱400,000. Calculate the company's contribution margin ratio.",
            "A. 25%, B. 50%, C. 75%, D. 100%",
            1, // Index of correct answer (A)
            "Hard"
        )

        insertQuizData(
            "Business Mathematics",
            "If a company's fixed costs are ₱120,000, its variable costs are ₱30 per unit, and it sells each unit for ₱60, what is the company's break-even point in units?",
            "A. 2,000 units, B. 3,000 units, C. 4,000 units, D. 5,000 units",
            3, // Index of correct answer (C)
            "Hard"
        )

        insertQuizData(
            "Business Mathematics",
            "A store bought 150 units of a product for ₱15 each and sold them for ₱25 each. What is the store's total profit?",
            "A. ₱1,500, B. ₱2,250, C. ₱3,000, D. ₱3,750",
            2, // Index of correct answer (C)
            "Hard"
        )

        insertQuizData(
            "Business Mathematics",
            "If a company's revenue is ₱3,000,000 and its total costs are ₱2,000,000, what is the company's profit margin in percentage?",
            "A. 25%, B. 33.33%, C. 50%, D. 66.67%",
            1, // Index of correct answer (A)
            "Hard"
        )

        insertQuizData(
            "Business Mathematics",
            "A company's total revenue is ₱3,000,000 and its total variable costs are ₱1,200,000. If the company's fixed costs are ₱800,000, its semi-variable costs are ₱200,000, its step costs are ₱100,000, and its joint costs are ₱50,000, calculate the company's operating income.",
            "A. ₱750,000, B. ₱800,000, C. ₱850,000, D. ₱900,000",
            2, // Index of correct answer (C)
            "Hard"
        )

        insertQuizData(
            "Business Mathematics",
            "If a company's fixed costs are ₱500,000, its variable costs are ₱120 per unit, its semi-variable costs are ₱50,000, its step costs are ₱30,000, and it sells each unit for ₱200, what is the company's break-even point in units?",
            "A. 2,000 units, B. 2,500 units, C. 3,000 units, D. 3,500 units",
            1, // Index of correct answer (A)
            "Hard"
        )

        insertQuizData(
            "Business Mathematics",
            "A store bought 1,000 units of a product for ₱50 each and sold them for ₱100 each. Considering additional costs of ₱20,000, step costs of ₱10,000, and joint costs of ₱5,000, what is the store's total profit?",
            "A. ₱45,000, B. ₱50,000, C. ₱55,000, D. ₱60,000",
            3, // Index of correct answer (D)
            "Hard"
        )

        insertQuizData(
            "Business Mathematics",
            "If a company's revenue is ₱10,000,000 and its total costs are ₱5,500,000, including fixed costs of ₱1,500,000, variable costs of ₱2,500,000, semi-variable costs of ₱500,000, step costs of ₱200,000, and joint costs of ₱100,000, what is the company's profit margin in percentage?",
            "A. 45%, B. 50%, C. 55%, D. 60%",
            2, // Index of correct answer (C)
            "Hard"
        )

        insertQuizData(
            "Business Mathematics",
            "A company's total revenue is ₱50,000,000 and its total variable costs are ₱20,000,000. If the company's fixed costs are ₱12,000,000, its semi-variable costs are ₱5,000,000, its step costs are ₱2,500,000, its joint costs are ₱1,000,000, its sunk costs are ₱500,000, its opportunity costs are ₱250,000, and its contingency costs are ₱100,000, calculate the company's operating income.",
            "A. ₱9,250,000, B. ₱9,500,000, C. ₱9,750,000, D. ₱10,000,000",
            2, // Index of correct answer (C)
            "Hard"
        )

        insertQuizData(
            "Business Mathematics",
            "If a company's fixed costs are ₱3,5000,,, its variable costs are ₱400 per unit,, its semi-variable costs are ₱200,,000,, its step costs are ₱100,,000,, and it sells each unit for ₱1,,500,, what is the company's break-even point in units?",
            "A. 2,,500 units,, B. 3,,000 units,, C. 3,,500 units,, D. 4,,000 units",
            0, // Index of correct answer (A)
            "Hard"
        )

        insertQuizData(
            "Business Mathematics",
            "A store bought 20,,000 units of a product for ₱300 each and sold them for ₱1,,200 each. Considering additional costs of ₱150,,000,,, step costs of ₱75,,000,,, joint costs of ₱60,,000,,, sunk costs of ₱30,,000,,, opportunity costs of ₱15,,000,,, and contingency costs of ₱10,,000,,, what is the store's total profit?",
            "A. ₱21,,5000,,, B. ₱22,,00,,, C. ₱22,,50,,, D. ₱23",
            3, // Index of correct answer (D)
            "Hard"
        )

        insertQuizData(
            "Business Mathematics",
            "If a company's revenue is ₱1000,,, and its total costs are ₱55,,,, including fixed costs of ₱15,,,, variable costs of ₱25,,,, semi-variable costs of ₱7,,,, step costs of ₱12,,,, joint costs of ₱6,,,, sunk costs of ₱3,,,, opportunity costs of ₱1,,,, and contingency costs of 5000,,, what is the company's profit margin in percentage?",
            "A. 45%, B. 50%, C. 55%, D. 60%",
            1,// Index of correct answer (B)
            "Hard"
        )

        insertQuizData(
            "Business Mathematics",
            "A company's total revenue is ₱20,000,000 and its total variable costs are ₱8,000,000. If the company's fixed costs are ₱5,000,000, its semi-variable costs are ₱2,000,000, its step costs are ₱1,000,000, its joint costs are ₱500,000, its sunk costs are ₱200,000, and its opportunity costs are ₱100,000, calculate the company's operating income.",
            "A. ₱3,500,000, B. ₱4,000,000, C. ₱4,500,000, D. ₱5,000,000",
            2, // Index of correct answer (C)
            "Hard"
        )

        insertQuizData(
            "Business Mathematics",
            "If a company's fixed costs are ₱2,000,000, its variable costs are ₱300 per unit, its semi-variable costs are ₱150,000, its step costs are ₱75,000, and it sells each unit for ₱1,000, what is the company's break-even point in units?",
            "A. 2,500 units, B. 3,000 units, C. 3,500 units, D. 4,000 units",
            0, // Index of correct answer (A)
            "Hard"
        )

        insertQuizData(
            "Business Mathematics",
            "A store bought 10,000 units of a product for ₱200 each and sold them for ₱800 each. Considering additional costs of ₱100,000, step costs of ₱50,000, joint costs of ₱40,000, sunk costs of ₱20,000, and opportunity costs of ₱10,000, what is the store's total profit?",
            "A. ₱6,500,000, B. ₱7,000,000, C. ₱7,500,000, D. ₱8,000,000",
            3, // Index of correct answer (D)
            "Hard"
        )

        insertQuizData(
            "Business Mathematics",
            "If a company's revenue is ₱80,000,000 and its total costs are ₱45,000,000, including fixed costs of ₱15,000,000, variable costs of ₱25,000,000, semi-variable costs of ₱7,000,000, step costs of ₱12,000,000, joint costs of ₱6,000,000, sunk costs of ₱3,000,000, and opportunity costs of ₱1,500,000, what is the company's profit margin in percentage?",
            "A. 45%, B. 50%, C. 55%, D. 60%",
            1, // Index of correct answer (B)
            "Hard"
        )

    }

    private fun populateEasyQuestionsBUSINESS() {
        insertQuizData(
            "Business Finance",
            "What is the difference between debit and credit in accounting?",
            "A. Debits increase expenses and credits increase assets, B. Credits increase expenses and debits increase assets, C. Debits and credits always decrease accounts, D. Credits and debits have no impact on accounts",
            0, // Index of correct answer (A)
            "Easy"
        )

        insertQuizData(
            "Business Finance",
            "What is the primary source of funding for most businesses?",
            "A. Bank loans, B. Venture capital, C. Personal savings, D. Share issuance",
            1, // Index of correct answer (A)
            "Easy"
        )

        insertQuizData(
            "Business Finance",
            "What does ROI stand for in finance?",
            "A. Return on Investment, B. Rate of Interest, C. Revenue of Income, D. Return on Income",
            0, // Index of correct answer (A)
            "Easy"
        )

        insertQuizData(
            "Business Finance",
            "Which financial statement provides a snapshot of a company's financial position at a specific point in time?",
            "A. Income statement, B. Statement of cash flows, C. Balance sheet, D. Statement of retained earnings",
            2, // Index of correct answer (C)
            "Easy"
        )

        insertQuizData(
            "Business Finance",
            "What does the term 'liquidity' refer to in finance?",
            "A. Ability to pay off long-term debt, B. Ability to convert assets into cash quickly, C. Ability to attract investors, D. Ability to generate revenue",
            1, // Index of correct answer (B)
            "Easy"
        )

        insertQuizData(
            "Business Finance",
            "What is the term for the money a company brings in from selling goods or services?",
            "A. Profit, B. Revenue, C. Cost, D. Investment",
            1, // Index of correct answer (B)
            "Easy"
        )
        insertQuizData(
            "Business Finance",
            "Which of the following is NOT a common source of financing for a business?",
            "A. Equity capital (selling shares), B. Debt financing (loans), C. Retained earnings (profits kept by the company), D. Government grants",
            3, // Index of correct answer (D)
            "Easy"
        )
        insertQuizData(
            "Business Finance",
            "What is the main purpose of a business budget?",
            "A. To track past expenses, B. To forecast future income and expenses, C. To value the company, D. To manage employee salaries",
            1, // Index of correct answer (B)
            "Easy"
        )

        insertQuizData(
            "Business Finance",
            "A company's ______ refers to the money it owes to creditors.",
            "A. Equity, B. Debt, C. Revenue, D. Assets",
            1, // Index of correct answer (B)
            "Easy"
        )
    }

    private fun populateMediumQuestionsBUSINESS() {
        // Add medium Business Finance quiz questions here
        insertQuizData(
            "Business Finance",
            "What term refers to funds provided by lenders or investors to enable businesses to operate?",
            "A. Equity, B. Assets, C. Liabilities, D. Capital",
            3, // Index of correct answer (D)
            "Medium"
        )

        insertQuizData(
            "Business Finance",
            "Which financial metric measures a company's ability to generate profit from its resources?",
            "A. Return on investment (ROI), B. Return on assets (ROA), C. Earnings per share (EPS), D. Price-earnings ratio (P/E ratio)",
            2, // Index of correct answer (B)
            "Medium"
        )

        insertQuizData(
            "Business Finance",
            "What is the term for the process of converting assets into cash?",
            "A. Amortization, B. Depreciation, C. Liquidity, D. Solvency",
            3, // Index of correct answer (C)
            "Medium"
        )
        insertQuizData(
            "Business Finance",
            "What is the formula to calculate the current ratio?",
            "A. Current assets / Current liabilities, B. Current liabilities / Current assets, C. Total assets / Total liabilities, D. Total liabilities / Total assets",
            1, // Index of correct answer (A)
            "Medium"
        )

        insertQuizData(
            "Business Finance",
            "Which financial ratio measures a company's ability to pay off its short-term liabilities with its short-term assets?",
            "A. Debt-to-Equity Ratio, B. Current Ratio, C. Return on Equity, D. Gross Profit Margin",
            2, // Index of correct answer (B)
            "Medium"
        )

        insertQuizData(
            "Business Finance",
            "What does the term 'EBITDA' stand for in finance?",
            "A. Earnings Before Interest, Taxes, Depreciation, and Amortization, B. Earnings Before Interest, Taxes, Depreciation, and Assets, C. Earnings Before Interest, Taxes, Dividends, and Amortization, D. Earnings Before Income, Taxes, Depreciation, and Assets",
            1, // Index of correct answer (A)
            "Medium"
        )

        insertQuizData(
            "Business Finance",
            "What does the term 'working capital' represent?",
            "A. Total assets minus total liabilities, B. Total liabilities minus total assets, C. Current assets minus current liabilities, D. Current liabilities minus current assets",
            3, // Index of correct answer (C)
            "Medium"
        )
        insertQuizData(
            "Business Finance",
            "What does the term 'WACC' stand for in finance?",
            "A. Weighted Average Cost of Capital, B. Weighted Average Cost of Cash, C. Weighted Annual Cash Cost, D. Weighted Annual Capital Cost",
            1, // Index of correct answer (A)
            "Medium"
        )

        insertQuizData(
            "Business Finance",
            "What financial metric measures the efficiency of a company's operations in generating profit from its revenue?",
            "A. Gross profit margin, B. Operating profit margin, C. Net profit margin, D. EBITDA margin",
            2, // Index of correct answer (B)
            "Medium"
        )

        insertQuizData(
            "Business Finance",
            "Which financial statement shows a company's financial position at a specific point in time?",
            "A. Income statement, B. Balance sheet, C. Cash flow statement, D. Statement of retained earnings",
            2, // Index of correct answer (B)
            "Medium"
        )

        insertQuizData(
            "Business Finance",
            "What is the term for the measure of how much profit a company generates with its shareholders' equity?",
            "A. Return on Investment (ROI), B. Return on Equity (ROE), C. Return on Assets (ROA), D. Net Profit Margin",
            2, // Index of correct answer (B)
            "Medium"
        )

        insertQuizData(
            "Business Finance",
            "Which financial ratio measures a company's ability to cover its interest expenses with its earnings?",
            "A. Debt-to-Equity Ratio, B. Interest Coverage Ratio, C. Quick Ratio, D. Debt Ratio",
            2, // Index of correct answer (B)
            "Medium"
        )

        insertQuizData(
            "Business Finance",
            "What is the term for the process of estimating the value of an asset or investment?",
            "A. Budgeting, B. Forecasting, C. Valuation, D. Amortization",
            3, // Index of correct answer (C)
            "Medium"
        )

        insertQuizData(
            "Business Finance",
            "Which financial metric measures the profitability of a company's core business operations?",
            "A. Earnings Before Interest and Taxes (EBIT), B. Gross Profit, C. Operating Income, D. Net Income",
            3, // Index of correct answer (C)
            "Medium"
        )

        insertQuizData(
            "Business Finance",
            "What term refers to the process of spreading out the cost of an intangible asset over its useful life?",
            "A. Amortization, B. Depreciation, C. Accrual, D. Impairment",
            1, // Index of correct answer (A)
            "Medium"
        )

        insertQuizData(
            "Business Finance",
            "What is the formula to calculate the debt-to-equity ratio?",
            "A. Total debt / Total equity, B. Total equity / Total debt, C. Total assets / Total equity, D. Total equity / Total assets",
            1, // Index of correct answer (A)
            "Medium"
        )

        insertQuizData(
            "Business Finance",
            "What does the term 'EBIT' stand for in finance?",
            "A. Earnings Before Interest and Taxes, B. Earnings Before Income and Taxes, C. Earnings Before Interest and Total Expenses, D. Earnings Before Interest and Depreciation",
            1, // Index of correct answer (A)
            "Medium"
        )

        insertQuizData(
            "Business Finance",
            "Which financial ratio measures a company's ability to meet its short-term obligations with its most liquid assets?",
            "A. Quick Ratio, B. Debt-to-Equity Ratio, C. Return on Assets, D. Gross Profit Margin",
            1, // Index of correct answer (A)
            "Medium"
        )

        insertQuizData(
            "Business Finance",
            "What is the formula to calculate the operating cash flow?",
            "A. Net Income + Depreciation, B. Net Income - Depreciation, C. Net Income + Interest, D. Net Income - Interest",
            1, // Index of correct answer (A)
            "Medium"
        )

        insertQuizData(
            "Business Finance",
            "What is the primary purpose of financial leverage in business?",
            "A. To reduce financial risk, B. To increase profitability, C. To increase return on investment, D. To increase financial flexibility",
            3, // Index of correct answer (C)
            "Medium"
        )

        insertQuizData(
            "Business Finance",
            "Which financial metric indicates the proportion of profits that a company returns to its shareholders in the form of dividends?",
            "A. Dividend Yield, B. Earnings per Share (EPS), C. Price-Earnings Ratio (P/E Ratio), D. Dividend Payout Ratio",
            4, // Index of correct answer (D)
            "Medium"
        )

        insertQuizData(
            "Business Finance",
            "What does the term 'IRR' stand for in finance?",
            "A. Internal Rate of Return, B. Interest Rate Reduction, C. Investment Return Ratio, D. Inflation Risk Reduction",
            1, // Index of correct answer (A)
            "Medium"
        )

        insertQuizData(
            "Business Finance",
            "Which financial metric measures the proportion of debt in a company's capital structure?",
            "A. Debt-to-Equity Ratio, B. Return on Investment (ROI), C. Price-Earnings Ratio (P/E Ratio), D. Current Ratio",
            1, // Index of correct answer (A)
            "Medium"
        )

        insertQuizData(
            "Business Finance",
            "What is the term for the process of distributing the cost of an intangible asset over its useful life?",
            "A. Amortization, B. Depreciation, C. Impairment, D. Accrual",
            1, // Index of correct answer (A)
            "Medium"
        )

        insertQuizData(
            "Business Finance",
            "Which financial statement shows changes in a company's equity over a specific period of time?",
            "A. Income statement, B. Balance sheet, C. Statement of cash flows, D. Statement of retained earnings",
            4, // Index of correct answer (D)
            "Medium"
        )

        insertQuizData(
            "Business Finance",
            "What does the term 'CAPM' stand for in finance?",
            "A. Capital Asset Pricing Model, B. Cash Asset Pricing Model, C. Corporate Asset Pricing Model, D. Credit Asset Pricing Model",
            1, // Index of correct answer (A)
            "Medium"
        )

        insertQuizData(
            "Business Finance",
            "Identify the type of financial ratio that measures a company's ability to meet its short-term obligations.",
            "A. Liquidity ratio, B. Profitability ratio, C. Efficiency ratio, D. Leverage ratio",
            0, // Index of correct answer (A)
            "Medium"
        )

        insertQuizData(
            "Business Finance",
            "Enumerate the three main components of a company's current assets.",
            "A. Cash, Accounts Receivable, Inventory, B. Cash, Accounts Payable, Inventory, C. Cash, Accounts Receivable, Fixed Assets, D. Cash, Accounts Payable, Fixed Assets",
            0, // Index of correct answer (A)
            "Medium"
        )

        insertQuizData(
            "Business Finance",
            "Which of the following is NOT a tool used in financial planning?",
            "A. Budgeting, B. Event planning, C. Working capital management, D. Loan amortization",
            3, // Index of correct answer (D)
            "Medium"
        )

        insertQuizData(
            "Business Finance",
            "Identify the type of financing that is typically used for long-term investments and capital expenditures.",
            "A. Short-term financing, B. Long-term financing, C. Equity financing, D. Debt financing",
            1, // Index of correct answer (B)
            "Medium"
        )

        insertQuizData(
            "Business Finance",
            "Enumerate the three main components of a company's cash flow statement.",
            "A. Operating activities, Investing activities, Financing activities, B. Revenue, Expenses, Profit, C. Assets, Liabilities, Equity, D. Accounts Receivable, Accounts Payable, Inventory",
            0, // Index of correct answer (A)
            "Medium"
        )

        insertQuizData(
            "Business Finance",
            "Which of the following is NOT a type of interest calculation method?",
            "A. Simple interest, B. Compound interest, C. Annuity, D. Present value",
            2, // Index of correct answer (C)
            "Medium"
        )

        insertQuizData(
            "Business Finance",
            "Identify the type of financial ratio that measures a company's ability to generate profits from its operations.",
            "A. Liquidity ratio, B. Profitability ratio, C. Efficiency ratio, D. Leverage ratio",
            1, // Index of correct answer (B)
            "Medium"
        )

        insertQuizData(
            "Business Finance",
            "Enumerate the three main components of a company's income statement.",
            "A. Revenue, Expenses, Net Income, B. Assets, Liabilities, Equity, C. Operating activities, Investing activities, Financing activities, D. Current assets, Non-current assets, Current liabilities",
            0, // Index of correct answer (A)
            "Medium"
        )

        insertQuizData(
            "Business Finance",
            "Which of the following is NOT a type of financial risk?",
            "A. Market risk, B. Credit risk, C. Operational risk, D. Liquidity risk",
            2, // Index of correct answer (C)
            "Medium"
        )

        insertQuizData(
            "Business Finance",
            "Identify the type of financial ratio that measures a company's ability to manage its assets efficiently.",
            "A. Liquidity ratio, B. Profitability ratio, C. Efficiency ratio, D. Leverage ratio",
            2, // Index of correct answer (C)
            "Medium"
        )

        insertQuizData(
            "Business Finance",
            "Enumerate the three main components of a company's balance sheet.",
            "A. Assets, Liabilities, Equity, B. Revenue, Expenses, Net Income, C. Operating activities, Investing activities, Financing activities, D. Current ratio, Quick ratio, Cash ratio",
            0, // Index of correct answer (A)
            "Medium"
        )

        insertQuizData(
            "Business Finance",
            "Which of the following is NOT a type of financial analysis technique?",
            "A. Ratio analysis, B. Horizontal analysis, C. Vertical analysis, D. Loan amortization",
            3, // Index of correct answer (D)
            "Medium"
        )

        insertQuizData(
            "Business Finance",
            "Identify the type of financial ratio that measures a company's ability to meet its long-term obligations.",
            "A. Liquidity ratio, B. Profitability ratio, C. Efficiency ratio, D. Leverage ratio",
            3, // Index of correct answer (D)
            "Medium"
        )

        insertQuizData(
            "Business Finance",
            "Enumerate the three main types of financial statements.",
            "A. Income statement, Balance sheet, Cash flow statement, B. Assets, Liabilities, Equity, C. Revenue, Expenses, Net Income, D. Operating activities, Investing activities, Financing activities",
            0, // Index of correct answer (A)
            "Medium"
        )

        insertQuizData(
            "Business Finance",
            "Which of the following is NOT a type of financial planning tool?",
            "A. Budgeting, B. Working capital management, C. Event planning, D. Loan amortization",
            3, // Index of correct answer (D)
            "Medium"
        )
    }

    private fun populateHardQuestionsBUSINESS() {
        // Add hard Business Finance quiz questions here

        insertQuizData(
            "Business Finance",
            "A company has a current ratio of 1.8 and a quick ratio of 1.2. If its current assets are ₱900,000, what is the value of its inventory?",
            "A. ₱300,000, B. ₱400,000, C. ₱500,000, D. ₱600,000",
            1, // Index of correct answer (B)
            "Hard"
        )

        insertQuizData(
            "Business Finance",
            "Company A has a net profit margin of 10%, while Company B has a net profit margin of 15%. If both companies have the same revenue of ₱10 million, what is the difference in their net profits?",
            "A. ₱500,000, B. ₱1,000,000, C. ₱1,500,000, D. ₱2,000,000",
            2, // Index of correct answer (C)
            "Hard"
        )

        insertQuizData(
            "Business Finance",
            "A company has a total asset turnover ratio of 1.5 and a fixed asset turnover ratio of 3.0. If the company's total revenue is ₱12 million, calculate the value of its fixed assets.",
            "A. ₱2 million, B. ₱3 million, C. ₱4 million, D. ₱6 million",
            2, // Index of correct answer (C)
            "Hard"
        )

        insertQuizData(
            "Business Finance",
            "A firm has a debt-to-equity ratio of 0.8 and total equity of ₱4 million. If the firm wants to maintain the same debt-to-equity ratio and issue additional equity of ₱1 million, how much additional debt should it take on?",
            "A. ₱400,000, B. ₱600,000, C. ₱800,000, D. ₱1,000,000",
            2, // Index of correct answer (C)
            "Hard"
        )

        insertQuizData(
            "Business Finance",
            "A company is planning an event that is expected to generate revenue of ₱500,000 and incur expenses of ₱400,000. If the company wants to achieve a profit margin of 25%, what should be the selling price of the event?",
            "A. ₱625,000, B. ₱666,666.67, C. ₱600,000, D. ₱533,333.33",
            1, // Index of correct answer (B)
            "Hard"
        )

        insertQuizData(
            "Business Finance",
            "A company needs ₱200,000 in 3 years for expansion. How much should they invest now at an annual interest rate of 12% compounded annually to meet this goal?",
            "A. ₱133,333.33, B. ₱145,212.30, C. ₱120,000.00, D. ₱156,324.17",
            0, // Index of correct answer (A)
            "Hard"
        )

        insertQuizData(
            "Business Finance",
            "A business requires ₱1.2 million to finance its operations for the next year. The company can either take out a short-term loan at 10% interest or issue long-term bonds at 8% interest. If the company expects to generate ₱1.5 million in cash flows, which option should it choose to maximize its profitability?",
            "A. Short-term loan, B. Long-term bonds, C. Both options are equally profitable, D. Neither option is profitable",
            1, // Index of correct answer (B)
            "Hard"
        )

        insertQuizData(
            "Business Finance",
            "A company is considering two mutually exclusive projects. Project A has an initial investment of ₱2 million and is expected to generate cash flows of ₱500,000 per year for the next 6 years. Project B has an initial investment of ₱3 million and is expected to generate cash flows of ₱800,000 per year for the next 5 years. Assuming a discount rate of 12%, which project should the company choose?",
            "A. Project A, B. Project B, C. Both projects are equally profitable, D. Neither project is profitable",
            0, // Index of correct answer (A)
            "Hard"
        )

        insertQuizData(
            "Business Finance",
            "A company has issued a 15-year bond with a face value of ₱5 million and a coupon rate of 8%. If the bond is currently trading at a price of ₱4.8 million, calculate the bond's yield to maturity.",
            "A. 7.5%, B. 8%, C. 8.5%, D. 9%",
            2, // Index of correct answer (C)
            "Hard"
        )

        insertQuizData(
            "Business Finance",
            "An investor has the option to receive ₱500,000 today or an annuity of ₱100,000 per year for the next 10 years. If the investor's required rate of return is 10%, which option should they choose?",
            "A. Lump sum of ₱500,000, B. Annuity of ₱100,000 per year, C. Both options are equally valuable, D. Neither option is valuable",
            1, // Index of correct answer (B)
            "Hard"
        )

        insertQuizData(
            "Business Finance",
            "A company has taken out a loan of ₱2 million with an interest rate of 8% and a term of 8 years. Calculate the total interest paid over the life of the loan using the prospective method.",
            "A. ₱640,000, B. ₱720,000, C. ₱800,000, D. ₱880,000",
            1, // Index of correct answer (B)
            "Hard"
        )

        insertQuizData(
            "Business Finance",
            "A company is considering two investment opportunities with the same expected return of 15%. Investment A has a standard deviation of 10%, while Investment B has a standard deviation of 12%. Which investment is riskier, and why?",
            "A. Investment A is riskier because it has a higher standard deviation, B. Investment B is riskier because it has a higher standard deviation, C. Both investments have the same level of risk, D. Risk cannot be determined from the given information",
            1, // Index of correct answer (B)
            "Hard"
        )

        insertQuizData(
            "Business Finance",
            "A company is considering taking out a loan of ₱5 million to finance a new project. The loan has an interest rate of 9% and a term of 10 years. If the company expects the project to generate an annual cash flow of ₱800,000, calculate the project's net present value (NPV) and internal rate of return (IRR).",
            "A. NPV: ₱1.2 million, IRR: 12%, B. NPV: ₱1.5 million, IRR: 14%, C. NPV: ₱2 million, IRR: 16%, D. NPV: ₱2.5 million, IRR: 18%",
            1, // Index of correct answer (B)
            "Hard"
        )

        insertQuizData(
            "Business Finance",
            "Company ABC has a debt-to-equity ratio of 1.0, while Company XYZ has a debt-to-equity ratio of 1.5. If both companies have the same total assets of ₱20 million, what are the respective amounts of debt and equity for each company?",
            "A. ABC: ₱10 million debt, ₱10 million equity; XYZ: ₱12 million debt, ₱8 million equity, B. ABC: ₱12 million debt, ₱8 million equity; XYZ: ₱15 million debt, ₱5 million equity, C. ABC: ₱8 million debt, ₱12 million equity; XYZ: ₱10 million debt, ₱10 million equity, D. ABC: ₱15 million debt, ₱5 million equity; XYZ: ₱18 million debt, ₱2 million equity",
            1, // Index of correct answer (B)
            "Hard"
        )

        insertQuizData(
            "Business Finance",
            "A company has a current ratio of 2.5 and a quick ratio of 1.8. If the company's current assets consist of cash, accounts receivable, and inventory, and the inventory is valued at ₱1 million, calculate the proportion of cash in the company's current assets.",
            "A. 20%, B. 25%, C. 30%, D. 35%",
            2, // Index of correct answer (C)
            "Hard"
        )

        insertQuizData(
            "Business Finance",
            "A company needs ₱200,000 in 3 years for expansion. How much should they invest now at an annual interest rate of 12% compounded annually to meet this goal?",
            "A. ₱133,333.33, B. ₱145,212.30, C. ₱120,000.00, D. ₱156,324.17",
            0, // Index of correct answer (A)
            "Hard"
        )

        insertQuizData(
            "Business Finance",
            "If a project requires an initial investment of ₱50,000 and is expected to generate ₱20,000 per year for the next 5 years, what is the payback period?",
            "A. 3 years, B. 2.5 years, C. 4 years, D. 2 years",
            1, // Index of correct answer (B)
            "Hard"
        )

        insertQuizData(
            "Business Finance",
            "Company A is considering investing ₱100,000 in a project expected to generate ₱30,000 per year for 6 years. If the discount rate is 10%, should they proceed with the investment?",
            "A. Yes, B. No",
            0, // Index of correct answer (A)
            "Hard"
        )

        insertQuizData(
            "Business Finance",
            "A company wants to accumulate ₱1,000,000 in 10 years for a new project. If they can earn 8% annually, how much should they invest now as a lump sum to achieve this goal?",
            "A. ₱466,096.77, B. ₱450,000.00, C. ₱550,000.00, D. ₱425,324.17",
            0, // Index of correct answer (A)
            "Hard"
        )

        insertQuizData(
            "Business Finance",
            "A company has a fixed cost of ₱50,000, a variable cost per unit of ₱10, and sells its product for ₱30 per unit. How many units must it sell to achieve a target profit of ₱20,000?",
            "A. 3,000 units, B. 4,000 units, C. 5,000 units, D. 6,000 units",
            1, // Index of correct answer (B)
            "Hard"
        )

        insertQuizData(
            "Business Finance",
            "Calculate the net present value (NPV) of an investment project that requires an initial outlay of ₱200,000 and is expected to generate cash inflows of ₱80,000 per year for the next 5 years. Use a discount rate of 12%.",
            "A. ₱30,000, B. ₱40,000, C. ₱50,000, D. ₱60,000",
            2, // Index of correct answer (C)
            "Hard"
        )

        insertQuizData(
            "Business Finance",
            "A company is considering two mutually exclusive projects. Project A requires an initial investment of ₱150,000 and is expected to generate cash flows of ₱50,000 per year for 4 years. Project B requires an initial investment of ₱200,000 and is expected to generate cash flows of ₱70,000 per year for 3 years. Which project should the company choose based on the payback period method if the company's maximum acceptable payback period is 3.5 years?",
            "A. Project A, B. Project B, C. Both projects are acceptable, D. Neither project is acceptable",
            1, // Index of correct answer (B)
            "Hard"
        )

        insertQuizData(
            "Business Finance",
            "Calculate the internal rate of return (IRR) for a project with an initial investment of ₱300,000 and cash inflows of ₱100,000 for each of the next 5 years.",
            "A. 10%, B. 15%, C. 20%, D. 25%",
            2, // Index of correct answer (C)
            "Hard"
        )
    }

    private fun populateEasyQuestionsFABM() {
        // Add easy Business Finance quiz questions here
        insertQuizData(
            "FABM 1",
            "What is the main purpose of accounting?",
            "A. To hire employees, B. To design websites, C. To advertise products, D. To record financial transactions",
            3, // Index of correct answer (D)
            "Easy"
        )

        insertQuizData(
            "FABM 1",
            "Who are the primary users of financial statements?",
            "A. Competitors and regulators, B. Marketers and advertisers, C. Employees and suppliers, D. Investors and creditors",
            3, // Index of correct answer (D)
            "Easy"
        )

        insertQuizData(
            "FABM 1",
            "Which form of business organization has unlimited liability for its owners?",
            "A. Limited Liability Company, B. Corporation, C. Partnership, D. Sole Proprietorship",
            3, // Index of correct answer (D)
            "Easy"
        )

        insertQuizData(
            "FABM 1",
            "Which type of business focuses on providing services rather than selling products?",
            "A. Retail business, B. Manufacturing business, C. Service business, D. Merchandising business",
            2, // Index of correct answer (C)
            "Easy"
        )

        insertQuizData(
            "FABM 1",
            "What is the accounting concept that assumes a business will continue to operate indefinitely?",
            "A. Materiality concept, B. Going concern concept, C. Conservatism principle, D. Matching principle",
            1, // Index of correct answer (B)
            "Easy"
        )

        insertQuizData(
            "FABM 1",
            "What is the formula for the accounting equation?",
            "A. Assets - Liabilities = Equity, B. Assets / Liabilities = Equity, C. Assets x Liabilities = Equity, D. Assets = Liabilities + Equity",
            3, // Index of correct answer (D)
            "Easy"
        )

        insertQuizData(
            "FABM 1",
            "Which of the following is an example of an operating expense?",
            "A. Purchase of equipment, B. Payment of salaries to employees, C. Repayment of a loan, D. Investment in stocks",
            1, // Index of correct answer (B)
            "Easy"
        )

        insertQuizData(
            "FABM 1",
            "What is the formula to calculate gross profit?",
            "A. Gross Profit = Revenue - Expenses, B. Gross Profit = Revenue / Expenses, C. Gross Profit = Revenue + Expenses, D. Gross Profit = Revenue x Expenses",
            0, // Index of correct answer (A)
            "Easy"
        )

        insertQuizData(
            "FABM 1",
            "Which of the following is a current asset?",
            "A. Land, B. Equipment, C. Accounts Receivable, D. Building",
            2, // Index of correct answer (C)
            "Easy"
        )

        insertQuizData(
            "FABM 1",
            "Which of the following is an example of an operating expense?",
            "A. Purchase of equipment, B. Payment of salaries to employees, C. Repayment of a loan, D. Investment in stocks",
            1, // Index of correct answer (B)
            "Easy"
        )

        insertQuizData(
            "FABM 1",
            "What is the formula to calculate gross profit?",
            "A. Gross Profit = Revenue - Expenses, B. Gross Profit = Revenue / Expenses, C. Gross Profit = Revenue + Expenses, D. Gross Profit = Revenue x Expenses",
            0, // Index of correct answer (A)
            "Easy"
        )

        insertQuizData(
            "FABM 1",
            "Which of the following is a current asset?",
            "A. Land, B. Equipment, C. Accounts Receivable, D. Building",
            2, // Index of correct answer (C)
            "Easy"
        )

        insertQuizData(
            "FABM 1",
            "Which of the following is a primary function of management?",
            "A. Planning, B. Recording, C. Summarizing, D. Posting",
            0, // Index of correct answer (A)
            "Easy"
        )

        insertQuizData(
            "FABM 1",
            "The process of recording financial transactions is known as:",
            "A. Analysis, B. Interpretation, C. Bookkeeping, D. Auditing",
            2, // Index of correct answer (C)
            "Easy"
        )

        insertQuizData(
            "FABM 1",
            "The accounting equation is expressed as:",
            "A. Assets = Liabilities + Owner's Equity, B. Assets = Liabilities - Owner's Equity, C. Assets + Liabilities = Owner's Equity, D. Assets - Liabilities = Owner's Equity",
            0, // Index of correct answer (A)
            "Easy"
        )

        insertQuizData(
            "FABM 1",
            "Which financial statement shows the financial position of a business at a specific point in time?",
            "A. Income Statement, B. Statement of Cash Flows, C. Balance Sheet, D. Statement of Retained Earnings",
            2, // Index of correct answer (C)
            "Easy"
        )

        insertQuizData(
            "FABM 1",
            "Revenue is recognized in the accounting records when:",
            "A. Cash is received, B. Goods are sold or services are rendered, C. An invoice is issued, D. Expenses are paid",
            1, // Index of correct answer (B)
            "Easy"
        )

        insertQuizData(
            "FABM 1",
            "Which of the following is a current asset?",
            "A. Land, B. Buildings, C. Accounts Receivable, D. Equipment",
            2, // Index of correct answer (C)
            "Easy"
        )

        insertQuizData(
            "FABM 1",
            "The financial statement that reports the revenues and expenses of a business for a specific period of time is the:",
            "A. Balance Sheet, B. Income Statement, C. Statement of Cash Flows, D. Statement of Owner's Equity",
            1, // Index of correct answer (B)
            "Easy"
        )

        insertQuizData(
            "FABM 1",
            "Which of the following is NOT a characteristic of a sole proprietorship?",
            "A. Limited liability, B. Single ownership, C. Simple to establish, D. Owner receives all profits",
            0, // Index of correct answer (A)
            "Easy"
        )

        insertQuizData(
            "FABM 1",
            "Which accounting principle states that accounting information should be based on actual cost?",
            "A. Going Concern, B. Historical Cost, C. Matching Principle, D. Revenue Recognition",
            1, // Index of correct answer (B)
            "Easy"
        )

        insertQuizData(
            "FABM 1",
            "The document prepared by a seller when goods are shipped to a customer is called a(n):",
            "A. Invoice, B. Purchase Order, C. Sales Order, D. Bill of Lading",
            3, // Index of correct answer (D)
            "Easy"
        )

        insertQuizData(
            "FABM 1",
            "What is the accounting concept that assumes a business will continue to operate indefinitely?",
            "A. Materiality concept, B. Matching principle, C. Going concern concept, D. Conservatism principle",
            2, // Index of correct answer (C)
            "Easy"
        )


        insertQuizData(
            "FABM 1",
            "What does ROI stand for in business analysis?",
            "A. Rate of Investment, B. Return on Income, C. Revenue over Income, D. Return on Investment",
            3, // Index of correct answer (D)
            "Easy"
        )
        insertQuizData(
            "FABM 1",
            "Which branch of accounting deals with the preparation of financial statements?",
            "A. Management Accounting, B. Tax Accounting, C. Financial Accounting, D. Cost Accounting",
            2, // Index of correct answer (C)
            "Easy"
        )

        insertQuizData(
            "FABM 1",
            "Who are the primary users of financial statements?",
            "A. Employees and suppliers, B. Competitors and regulators, C. Investors and creditors, D. Marketers and advertisers",
            2, // Index of correct answer (C)
            "Easy"
        )

        insertQuizData(
            "FABM 1",
            "Which form of business organization has unlimited liability for its owners?",
            "A. Partnership, B. Corporation, C. Sole Proprietorship, D. Limited Liability Company",
            2, // Index of correct answer (C)
            "Easy"
        )

        insertQuizData(
            "FABM 1",
            "Which type of business focuses on providing services rather than selling products?",
            "A. Manufacturing business, B. Retail business, C. Merchandising business, D. Service business",
            3, // Index of correct answer (D)
            "Easy"
        )

        insertQuizData(
            "Business",
            "What are the different types of business costs?",
            "A. Fixed and variable costs, B. Direct and indirect costs, C. Explicit and implicit costs, D. All of the above",
            3, // Index of correct answer (D)
            "Easy"
        )

    }

    private fun populateMediumQuestionsFABM() {
        // Add medium Business Finance quiz questions here
        insertQuizData(
            "FABM 1",
            "What is the purpose of a cash flow statement in financial reporting?",
            "A. To assess a company's marketing strategies, B. To calculate a company's return on investment, C. To provide information about a company's liquidity and solvency, D. To report a company's revenues and expenses",
            2, // Index of correct answer (C)
            "Medium"
        )

        insertQuizData(
            "FABM 1",
            "Define 'fixed costs' in business.",
            "A. Costs associated with marketing and advertising, B. Costs incurred for short-term projects, C. Costs that vary with the level of production or sales, D. Costs that remain constant regardless of the level of production or sales",
            3, // Index of correct answer (D)
            "Medium"
        )




        insertQuizData(
            "FABM 1",
            "Define 'operating income' in financial analysis.",
            "A. The revenue earned from non-operating activities, B. The total revenue generated by a company, C. The amount of profit retained by the company for future investments, D. The net income after deducting all expenses except taxes and interest",
            3, // Index of correct answer (D)
            "Medium"
        )

        insertQuizData(
            "FABM 1",
            "What is the purpose of a balance sheet in financial reporting?",
            "A. To report a company's revenues and expenses, B. To analyze the inflow and outflow of cash over a period of time, C. To list a company's assets, liabilities, and equity at a specific point in time, D. To provide information about a company's profitability over a period of time",
            2, // Index of correct answer (C)
            "Medium"
        )



        insertQuizData(
            "FABM 1",
            "What is the purpose of an income statement in financial reporting?",
            "A. To report a company's revenues and expenses over a period of time, B. To list a company's assets, liabilities, and equity at a specific point in time, C. To analyze the inflow and outflow of cash over a period of time, D. To provide information about a company's liquidity and solvency",
            0, // Index of correct answer (A)
            "Medium"
        )

        insertQuizData(
            "FABM 1",
            "Define 'accounts receivable' in accounting.",
            "A. The money owed by customers to a company for goods or services provided on credit, B. The money owed by a company to its suppliers for goods or services received on credit, C. The total amount of money a company receives from its customers, D. The total amount of money a company pays to its suppliers",
            0, // Index of correct answer (A)
            "Medium"
        )



        insertQuizData(
            "Finance",
            "What is the Capital Asset Pricing Model (CAPM) used for?",
            "A.  Calculating the expected return of an individual stock, B. Estimating the cost of equity capital for a company, C.  Evaluating the performance of a mutual fund, D. Comparing the risk and return of different investments",
            1, // Index of correct answer (B)
            "Medium"
        )

        insertQuizData(
            "Accounting",
            "What is the difference between IFRS and US GAAP?",
            "A. Both are the same set of accounting standards, B. IFRS are international standards, while US GAAP are specific to the United States, C. IFRS focus on cash flow, while US GAAP focus on profitability, D. None of the above",
            1, // Index of correct answer (B)
            "Medium"
        )


        insertQuizData(
            "FABM 1",
            "Which of the following is a characteristic of financial accounting?",
            "A. Focuses on providing information for internal decision-making, B. Prepared in accordance with generally accepted accounting principles (GAAP), C. Emphasizes future-oriented information, D. Primarily used by managers and employees",
            1, // Index of correct answer (B)
            "Medium"
        )

        insertQuizData(
            "FABM 1",
            "Which of the following is a characteristic of management accounting?",
            "A. Focuses on historical financial information, B. Primarily used by external users, C. Emphasizes precision over relevance, D. Provides information for internal decision-making",
            3, // Index of correct answer (D)
            "Medium"
        )

        insertQuizData(
            "FABM 1",
            "Which of the following is an example of an external audit?",
            "A. Internal auditor reviewing financial statements, B. Government agency reviewing tax returns, C. Company's management reviewing budget variances, D. Board of directors reviewing financial performance",
            1, // Index of correct answer (B)
            "Medium"
        )

        insertQuizData(
            "FABM 1",
            "What is the purpose of an external audit in accounting practices?",
            "A. To ensure compliance with tax laws, B. To review financial statements for accuracy, C. To assess budget variances, D. To evaluate financial performance",
            1, // Index of correct answer (B)
            "Medium"
        )

        insertQuizData(
            "FABM 1",
            "Which financial statement is typically reviewed during an external audit?",
            "A. Balance Sheet, B. Income Statement, C. Cash Flow Statement, D. Statement of Changes in Equity",
            0, // Index of correct answer (A)
            "Medium"
        )
        insertQuizData(
            "FABM 1",
            "Explain the difference between an internal audit and an external audit.",
            "A. Scope and objectives, B. Reporting structure, C. Timing of audit, D. Audit tools used",
            0, // Index of correct answer (A)
            "Medium"
        )

        insertQuizData(
            "FABM 1",
            "How does a government agency reviewing tax returns contribute to ensuring compliance?",
            "A. By providing tax advice, B. By auditing financial statements, C. By enforcing tax laws, D. By managing budget variances",
            2, // Index of correct answer (C)
            "Medium"
        )

        insertQuizData(
            "FABM 1",
            "Discuss the importance of independence and objectivity in external audit processes.",
            "A. It ensures accurate financial reporting, B. It maintains ethical standards, C. It enhances audit quality, D. It reduces audit risks",
            1, // Index of correct answer (B)
            "Medium"
        )

        insertQuizData(
            "FABM 1",
            "Why is professional skepticism crucial for external auditors?",
            "A. To increase audit fees, B. To maintain audit independence, C. To speed up audit processes, D. To avoid conflicts of interest",
            0, // Index of correct answer (A)
            "Medium"
        )

        insertQuizData(
            "FABM 1",
            "Identify the key stakeholders benefiting from external audit outcomes.",
            "A. Shareholders, B. Management, C. Regulators, D. All of the above",
            3, // Index of correct answer (D)
            "Medium"
        )

        insertQuizData(
            "FABM 1",
            "What are the common procedures followed by external auditors during audits?",
            "A. Risk assessment, B. Testing controls, C. Analyzing financial data, D. All of the above",
            3, // Index of correct answer (D)
            "Medium"
        )

        insertQuizData(
            "FABM 1",
            "Explain the concept of materiality in external audit reporting.",
            "A. It focuses on insignificant errors, B. It considers the impact of errors on users, C. It ignores financial statement errors, D. It prioritizes audit fees",
            1, // Index of correct answer (B)
            "Medium"
        )

        insertQuizData(
            "FABM 1",
            "Discuss the role of audit evidence in supporting external audit conclusions.",
            "A. It is unnecessary for audit reports, B. It provides credibility to audit findings, C. It delays audit processes, D. It increases audit costs",
            1, // Index of correct answer (B)
            "Medium"
        )

    }

    private fun populateHardQuestionsFABM() {
        // Add hard Business Finance quiz questions here

        insertQuizData(
            "FABM 1",
            "A sole proprietorship business had the following transactions during its first month of operations: (1) The owner invested ₱500,000 cash to start the business, (2) Purchased equipment worth ₱200,000 on credit, (3) Paid ₱50,000 for rent and utilities, (4) Earned ₱150,000 in service revenue. Calculate the ending balance of the owner's capital at the end of the month.",
            "A. ₱350,000, B. ₱400,000, C. ₱600,000, D. ₱800,000",
            2, // Index of correct answer (C)
            "Hard"
        )

        insertQuizData(
            "FABM 1",
            "A partnership firm has two partners, Alex and Bea, with a capital ratio of 2:3. During the year, the firm earned a net income of ₱900,000 and paid salaries of ₱200,000 to Alex and ₱300,000 to Bea. Calculate the amount of net income to be distributed to each partner.",
            "A. Alex: ₱280,000 Bea: ₱420,000, B. Alex: ₱320,000 Bea: ₱480,000, C. Alex: ₱360,000 Bea: ₱540,000, D. Alex: ₱400,000 Bea: ₱600,000",
            1, // Index of correct answer (B)
            "Hard"
        )

        insertQuizData(
            "FABM 1",
            "A manufacturing company had the following transactions during the year: (1) Purchased raw materials for ₱500,000, (2) Incurred direct labor costs of ₱300,000, (3) Incurred manufacturing overhead costs of ₱200,000, (4) Completed goods with a total cost of ₱800,000, (5) Sold goods with a total cost of ₱600,000. Calculate the cost of goods manufactured and the cost of goods sold for the year.",
            "A. Cost of goods manufactured: ₱800,000 Cost of goods sold: ₱600 000, B. Cost of goods manufactured: ₱1 000 000 Cost of goods sold: ₱800 000, C. Cost of goods manufactured: ₱800 000 Cost of goods sold: ₱800 000, D. Cost of goods manufactured: ₱1 000 000 Cost of goods sold: ₱600,000",
            3, // Index of correct answer (C)
            "Hard"
        )

        insertQuizData(
            "FABM 1",
            "A company has the following account balances: Cash ₱100,000, Accounts Receivable ₱50,000, Inventory ₱80,000, Equipment ₱200,000, Accounts Payable ₱60,000, and a Loan Payable of ₱120 000. If the company's total assets are ₱430,000, calculate the amount of the company's owner's equity.",
            "A. ₱200,000, B. ₱250,000, C. ₱300,000, D. ₱350,000",
            0, // Index of correct answer (A)
            "Hard"
        )

        insertQuizData(
            "FABM 1",
            "A company uses the accrual basis of accounting. During the year, the company earned ₱1,200,000 in revenue, of which ₱300,000 was received in advance from customers. The company also incurred ₱900,000 in expenses, of which ₱200,000 was paid in the following year. Calculate the company's net income for the year.",
            "A. ₱100,000, B. ₱200,000, C. ₱300,000, D. ₱500,000",
            2, // Index of correct answer (C)
            "Hard"
        )

        insertQuizData(
            "FABM 1",
            "A company is considering two mutually exclusive investment opportunities. Investment A requires an initial outlay of ₱500,000 and is expected to generate cash inflows of ₱200,000 per year for 4 years. Investment B requires an initial outlay of ₱800,000 and is expected to generate cash inflows of ₱300,000 per year for 5 years. Assuming a discount rate of 10%, which investment should the company choose?",
            "A. Investment A, B. Investment B, C. Both investments are equally desirable, D. Neither investment is desirable",
            1, // Index of correct answer (B)
            "Hard"
        )

        insertQuizData(
            "FABM 1",
            "A company had the following transactions: (1) Issued ₱1 000 000 in common stock, (2) Purchased equipment for ₱400 000 paying ₱200 000 in cash and the rest on credit, (3) Paid ₱100 000 in salaries, (4) Earned ₱600 000 in revenue. Calculate the company's ending retained earnings balance.",
            "A. ₱300 000, B. ₱400 000, C. ₱500 000, D. ₱600 000",
            2, // Index of correct answer (C)
            "Hard"
        )

        insertQuizData(
            "FABM 1",
            "A company had the following account balances: Cash ₱500 000, Accounts Receivable ₱200 000, Inventory ₱300 000, Land ₱1 000 000, Accounts Payable ₱400 000, Loan Payable ₱600 000. Calculate the company's working capital.",
            "A. ₱400 000, B. ₱600 000, C. ₱800 000, D. ₱1 000 000",
            1, // Index of correct answer (B)
            "Hard"
        )

        insertQuizData(
            "FABM 1",
            "A company had the following transactions: (1) Purchased raw materials for ₱200 000 on account, (2) Incurred direct labor costs of ₱150 000, (3) Incurred manufacturing overhead costs of ₱100 000, (4) Completed goods with a total cost of ₱400 000. Calculate the cost of goods manufactured.",
            "A. ₱350 000, B. ₱400 000, C. ₱450 000, D. ₱500 000",
            1, // Index of correct answer (B)
            "Hard"
        )

        insertQuizData(
            "FABM 1",
            "A company had the following transactions: (1) Earned ₱800 000 in revenue, (2) Incurred ₱500 000 in expenses, (3) Declared and paid ₱100 000 in dividends, (4) The company had a beginning retained earnings balance of ₱200 000. Calculate the company's ending retained earnings balance.",
            "A. ₱300 000, B. ₱400 000, C. ₱500 000, D. ₱600 000",
            0, // Index of correct answer (A)
            "Hard"
        )

        insertQuizData(
            "FABM 1",
            "A company uses the perpetual inventory system. At the beginning of the year, the company had ₱100 000 in inventory. During the year, the company made purchases of ₱400 000 and had sales of ₱600 000 with a gross profit of 30%. Calculate the company's ending inventory balance.",
            "A. ₱80 000, B. ₱100 000, C. ₱120 000, D. ₱140 000",
            0, // Index of correct answer (A)
            "Hard"
        )

        insertQuizData(
            "FABM 1",
            "A company had the following transactions: (1) Purchased equipment for ₱500 000 paying ₱200 000 in cash and the rest on credit, (2) Earned ₱800 000 in revenue, (3) Incurred ₱600 000 in expenses, (4) Paid ₱100 000 in dividends. Calculate the company's ending retained earnings balance if the beginning balance was ₱300 000.",
            "A. ₱400 000, B. ₱500 000, C. ₱600 000, D. ₱700 000",
            2, // Index of correct answer (C)
            "Hard"
        )

        insertQuizData(
            "FABM 1",
            "A company had the following transactions: (1) Issued ₱2 000 000 in common stock, (2) Purchased land for ₱1 000 000 paying ₱500 000 in cash and the rest on credit, (3) Paid ₱200 000 in salaries, (4) Earned ₱1 200 000 in revenue. Calculate the company's ending total assets.",
            "A. ₱2 500 000, B. ₱3 000 000, C. ₱3 500 000, D. ₱4 000 000",
            2, // Index of correct answer (C)
            "Hard"
        )

        insertQuizData(
            "FABM 1",
            "A company had the following account balances: Cash ₱700 000, Accounts Receivable ₱300 000, Inventory ₱400 000, Equipment ₱800 000, Accounts Payable ₱500 000, Loan Payable ₱700 000. Calculate the company's total liabilities.",
            "A. ₱1 000 000, B. ₱1 200 000, C. ₱1 400 000, D. ₱1 600 000",
            1, // Index of correct answer (B)
            "Hard"
        )

        insertQuizData(
            "FABM 1",
            "A company had the following transactions: (1) Purchased raw materials for ₱300 000 on account, (2) Incurred direct labor costs of ₱200 000, (3) Incurred manufacturing overhead costs of ₱150 000, (4) Completed goods with a total cost of ₱600 000. Calculate the cost of goods manufactured.",
            "A. ₱550 000, B. ₱600 000, C. ₱650 000, D. ₱700 000",
            1, // Index of correct answer (B)
            "Hard"
        )

        insertQuizData(
            "FABM 1",
            "A company had the following transactions: (1) Earned ₱1 000 000 in revenue, (2) Incurred ₱700 000 in expenses, (3) Declared and paid ₱150 000 in dividends, (4) The company had a beginning retained earnings balance of ₱400 000. Calculate the company's ending retained earnings balance.",
            "A. ₱400 000, B. ₱500 000, C. ₱550 000, D. ₱600 000",
            2, // Index of correct answer (C)
            "Hard"
        )

        insertQuizData(
            "FABM 1",
            "A company uses the perpetual inventory system. At the beginning of the year, the company had ₱150 000 in inventory. During the year, the company made purchases of ₱500 000 and had sales of ₱800 000 with a gross profit of 40%. Calculate the company's ending inventory balance.",
            "A. ₱110 000, B. ₱130 000, C. ₱150 000, D. ₱170 000",
            0, // Index of correct answer (A)
            "Hard"
        )

        insertQuizData(
            "FABM 1",
            "A company had the following transactions: (1) Purchased equipment for ₱700 000 paying ₱300 000 in cash and the rest on credit, (2) Earned ₱900 000 in revenue, (3) Incurred ₱650 000 in expenses, (4) Paid ₱120 000 in dividends. Calculate the company's ending retained earnings balance if the beginning balance was ₱400 000.",
            "A. ₱430 000, B. ₱480 000, C. ₱530 000, D. ₱580 000",
            2, // Index of correct answer (C)
            "Hard"
        )

        insertQuizData(
            "FABM 1",
            "A company had the following transactions: (1) Issued ₱3 000 000 in common stock, (2) Purchased equipment for ₱1 500 000 paying ₱600 000 in cash and the rest on credit, (3) Paid ₱300 000 in salaries, (4) Earned ₱1 800 000 in revenue. Calculate the company's ending total liabilities.",
            "A. ₱900 000, B. ₱1 200 000, C. ₱1 500 000, D. ₱1 800 000",
            1, // Index of correct answer (B)
            "Hard"
        )

        insertQuizData(
            "FABM 1",
            "A company had the following account balances: Cash ₱800 000, Accounts Receivable ₱400 000, Inventory ₱500 000, Equipment ₱1 000 000, Accounts Payable ₱600 000, Loan Payable ₱800 000. Calculate the company's owner's equity.",
            "A. ₱500 000, B. ₱700 000, C. ₱900 000, D. ₱1 100 000",
            2, // Index of correct answer (C)
            "Hard"
        )

        insertQuizData(
            "FABM 1",
            "A company had the following transactions: (1) Purchased raw materials for ₱400 000 on account, (2) Incurred direct labor costs of ₱250 000, (3) Incurred manufacturing overhead costs of ₱200 000, (4) Completed goods with a total cost of ₱800 000. Calculate the cost of goods manufactured.",
            "A. ₱750 000, B. ₱800 000, C. ₱850 000, D. ₱900 000",
            1, // Index of correct answer (B)
            "Hard"
        )

        insertQuizData(
            "FABM 1",
            "A company had the following transactions: (1) Earned ₱1 200 000 in revenue, (2) Incurred ₱800 000 in expenses, (3) Declared and paid ₱200 000 in dividends, (4) The company had a beginning retained earnings balance of ₱500 000. Calculate the company's ending retained earnings balance.",
            "A. ₱500 000, B. ₱600 000, C. ₱700 000, D. ₱800 000",
            2, // Index of correct answer (C)
            "Hard"
        )

        insertQuizData(
            "FABM 1",
            "A company uses the perpetual inventory system. At the beginning of the year, the company had ₱200 000 in inventory. During the year, the company made purchases of ₱600 000 and had sales of ₱1 000 000 with a gross profit of 30%. Calculate the company's ending inventory balance.",
            "A. ₱140 000, B. ₱160 000, C. ₱180 000, D. ₱200 000",
            1, // Index of correct answer (B)
            "Hard"
        )

        insertQuizData(
            "FABM 1",
            "A company had the following transactions: (1) Purchased equipment for ₱800 000 paying ₱400 000 in cash and the rest on credit, (2) Earned ₱1 000 000 in revenue, (3) Incurred ₱700 000 in expenses, (4) Paid ₱150 000 in dividends. Calculate the company's ending retained earnings balance if the beginning balance was ₱500 000.",
            "A. ₱550 000, B. ₱600 000, C. ₱650 000, D. ₱700 000",
            2, // Index of correct answer (C)
            "Hard"
        )

        insertQuizData(
            "FABM 1",
            "A company had the following transactions: (1) Issued ₱4 000 000 in common stock, (2) Purchased land for ₱2 000 000 paying ₱800 000 in cash and the rest on credit, (3) Paid ₱400 000 in salaries, (4) Earned ₱2 400 000 in revenue. Calculate the company's ending total assets.",
            "A. ₱5 200 000, B. ₱5 600 000, C. ₱6 000 000, D. ₱6 400 000",
            2, // Index of correct answer (C)
            "Hard"
        )

        insertQuizData(
            "FABM 1",
            "A company had the following account balances: Cash ₱900 000, Accounts Receivable ₱500 000, Inventory ₱600 000, Equipment ₱1 200 000, Accounts Payable ₱700 000, Loan Payable ₱900 000. Calculate the company's total liabilities.",
            "A. ₱1 400 000, B. ₱1 600 000, C. ₱1 800 000, D. ₱2 000 000",
            1, // Index of correct answer (B)
            "Hard"
        )

        insertQuizData(
            "FABM 1",
            "A company had the following transactions: (1) Purchased raw materials for ₱500 000 on account, (2) Incurred direct labor costs of ₱300 000, (3) Incurred manufacturing overhead costs of ₱250 000, (4) Completed goods with a total cost of ₱1 000 000. Calculate the cost of goods manufactured.",
            "A. ₱950 000, B. ₱1 000 000, C. ₱1 050 000, D. ₱1 100 000",
            1, // Index of correct answer (B)
            "Hard"
        )

        insertQuizData(
            "FABM 1",
            "A company had the following transactions: (1) Earned ₱1 500 000 in revenue, (2) Incurred ₱900 000 in expenses, (3) Declared and paid ₱250 000 in dividends, (4) The company had a beginning retained earnings balance of ₱600 000. Calculate the company's ending retained earnings balance.",
            "A. ₱650 000, B. ₱750 000, C. ₱850 000, D. ₱950 000",
            2, // Index of correct answer (C)
            "Hard"
        )

        insertQuizData(
            "FABM 1",
            "A company uses the perpetual inventory system. At the beginning of the year, the company had ₱250 000 in inventory. During the year, the company made purchases of ₱700 000 and had sales of ₱1 200 000 with a gross profit of 40%. Calculate the company's ending inventory balance.",
            "A. ₱190 000, B. ₱210 000, C. ₱230 000, D. ₱250 000",
            0, // Index of correct answer (A)
            "Hard"
        )

        insertQuizData(
            "FABM 1",
            "A company had the following transactions: (1) Purchased equipment for ₱900 000 paying ₱500 000 in cash and the rest on credit, (2) Earned ₱1 200 000 in revenue, (3) Incurred ₱800 000 in expenses, (4) Paid ₱200 000 in dividends. Calculate the company's ending retained earnings balance if the beginning balance was ₱600 000.",
            "A. ₱700 000, B. ₱750 000, C. ₱800 000, D. ₱850 000",
            2, // Index of correct answer (C)
            "Hard"
        )

    }


    private fun populateEasyQuestionsECONOMICS() {
        // Add easy Business Finance quiz questions here

        insertQuizData(
            "Applied Economics",
            "What is the term used to refer to the total value of all final goods and services produced within a country's borders in a given time period?",
            "A. GDP, B. GNP, C. CPI, D. Inflation",
            0, // Index of correct answer (A)
            "Easy"
        )

        insertQuizData(
            "Applied Economics",
            "What is the term used to describe the situation when the quantity demanded by buyers equals the quantity supplied by sellers?",
            "A. Equilibrium, B. Surplus, C. Shortage, D. Monopoly",
            0, // Index of correct answer (A)
            "Easy"
        )

        insertQuizData(
            "Applied Economics",
            "What is the term used to describe the measure of the responsiveness of quantity demanded to a change in price?",
            "A. Income Elasticity, B. Cross Elasticity, C. Price Elasticity, D. Supply Elasticity",
            2, // Index of correct answer (C)
            "Easy"
        )

        insertQuizData(
            "Applied Economics",
            "What is the term used to describe a market structure characterized by a single seller of a unique product with no close substitutes?",
            "A. Perfect Competition, B. Monopolistic Competition, C. Oligopoly, D. Monopoly",
            3, // Index of correct answer (D)
            "Easy"
        )

        insertQuizData(
            "Applied Economics",
            "What is the term used to describe the analysis of a company's internal strengths and weaknesses, as well as external opportunities and threats?",
            "A. Industry Analysis, B. Environmental Analysis, C. SWOT Analysis, D. Porter's Five Forces",
            2, // Index of correct answer (C)
            "Easy"
        )

        insertQuizData(
            "Applied Economics",
            "What is the term used to describe the measure of market concentration that takes into account the relative size and distribution of firms in a market?",
            "A. Consumer Theory, B. Utility Function, C. Herfindahl-Hirschman Index (HHI), D. Production Theory",
            2, // Index of correct answer (C)
            "Easy"
        )

        insertQuizData(
            "Applied Economics",
            "What is the term used to describe the study of how individuals and societies make choices to allocate scarce resources?",
            "A. Macroeconomics, B. Microeconomics, C. Economic Development, D. Economic History",
            1, // Index of correct answer (B)
            "Easy"
        )

        insertQuizData(
            "Applied Economics",
            "What is the term used to describe the measure of the responsiveness of quantity demanded to a change in consumer income?",
            "A. Price Elasticity, B. Cross Elasticity, C. Income Elasticity, D. Supply Elasticity",
            2, // Index of correct answer (C)
            "Easy"
        )

        insertQuizData(
            "Applied Economics",
            "What is the term used to describe the measure of the responsiveness of demand for one good to a change in the price of another good?",
            "A. Income Elasticity, B. Cross Elasticity, C. Price Elasticity, D. Supply Elasticity",
            1, // Index of correct answer (B)
            "Easy"
        )

        insertQuizData(
            "Applied Economics",
            "What is the term used to describe the market structure where there are many small firms selling similar but differentiated products?",
            "A. Perfect Competition, B. Monopolistic Competition, C. Oligopoly, D. Monopoly",
            1, // Index of correct answer (B)
            "Easy"
        )

        insertQuizData(
            "Applied Economics",
            "What is the term used to describe the analysis of a firm's competitive environment within a particular industry?",
            "A. Industry Analysis, B. Environmental Analysis, C. SWOT Analysis, D. Porter's Five Forces",
            0, // Index of correct answer (A)
            "Easy"
        )

        insertQuizData(
            "Applied Economics",
            "What is the term used to describe the study of the factors that influence the decisions of consumers in the allocation of their resources?",
            "A. Consumer Theory, B. Utility Function, C. Production Theory, D. Socioeconomic Impact",
            0, // Index of correct answer (A)
            "Easy"
        )

        insertQuizData(
            "Applied Economics",
            "What is the term used to describe the analysis of the impact of a business or industry on the surrounding community and economy?",
            "A. Consumer Theory, B. Utility Function, C. Production Theory, D. Socioeconomic Impact",
            3, // Index of correct answer (D)
            "Easy"
        )

        insertQuizData(
            "Applied Economics",
            "What is the term used to describe the analysis of the factors that influence the decisions of firms in the production of goods and services?",
            "A. Consumer Theory, B. Utility Function, C. Production Theory, D. Socioeconomic Impact",
            2, // Index of correct answer (C)
            "Easy"
        )

        insertQuizData(
            "Applied Economics",
            "What is the term used to describe the study of the behavior of the economy as a whole?",
            "A. Microeconomics, B. Macroeconomics, C. Economic Development, D. Economic History",
            1, // Index of correct answer (B)
            "Easy"
        )

        insertQuizData(
            "Applied Economics",
            "What is the term used to describe the analysis of the economic growth and development of a country or region over time?",
            "A. Macroeconomics, B. Microeconomics, C. Economic Development, D. Economic History",
            2, // Index of correct answer (C)
            "Easy"
        )

        insertQuizData(
            "Applied Economics",
            "What is the term used to describe the study of the economic events and processes in the past?",
            "A. Macroeconomics, B. Microeconomics, C. Economic Development, D. Economic History",
            3, // Index of correct answer (D)
            "Easy"
        )

        insertQuizData(
            "Applied Economics",
            "What is the term used to describe the payment made for the use of land or property?",
            "A. Wage, B. Interest, C. Profit, D. Rent",
            3, // Index of correct answer (D)
            "Easy"
        )

        insertQuizData(
            "Applied Economics",
            "What is the term used to describe the minimum hourly rate that employers are legally required to pay their workers?",
            "A. Living Wage, B. Minimum Wage, C. Fair Wage, D. Market Wage",
            1, // Index of correct answer (B)
            "Easy"
        )

        insertQuizData(
            "Applied Economics",
            "What is the term used to describe the situation where the quantity supplied is greater than the quantity demanded at the current price?",
            "A. Equilibrium, B. Surplus, C. Shortage, D. Monopoly",
            1, // Index of correct answer (B)
            "Easy"
        )

        insertQuizData(
            "Applied Economics",
            "What is the term used to describe the measure of the responsiveness of quantity supplied to a change in price?",
            "A. Income Elasticity, B. Cross Elasticity, C. Price Elasticity, D. Supply Elasticity",
            3, // Index of correct answer (D)
            "Easy"
        )

        insertQuizData(
            "Applied Economics",
            "What is the term used to describe the market structure where there are only a few large firms dominating the market?",
            "A. Perfect Competition, B. Monopolistic Competition, C. Oligopoly, D. Monopoly",
            2, // Index of correct answer (C)
            "Easy"
        )

        insertQuizData(
            "Applied Economics",
            "What is the term used to describe the analysis of the strengths, weaknesses, opportunities, and threats of a business or industry?",
            "A. Industry Analysis, B. Environmental Analysis, C. SWOT Analysis, D. Porter's Five Forces",
            2, // Index of correct answer (C)
            "Easy"
        )

        insertQuizData(
            "Applied Economics",
            "What is the term used to describe the analysis of the five competitive forces that shape the profitability of an industry?",
            "A. Industry Analysis, B. Environmental Analysis, C. SWOT Analysis, D. Porter's Five Forces",
            3, // Index of correct answer (D)
            "Easy"
        )

        insertQuizData(
            "Applied Economics",
            "What is the term used to describe the analysis of the external factors that can impact a business or industry?",
            "A. Industry Analysis, B. Environmental Analysis, C. SWOT Analysis, D. Porter's Five Forces",
            1, // Index of correct answer (B)
            "Easy"
        )

        insertQuizData(
            "Applied Economics",
            "What is the term used to describe the analysis of the relative satisfaction or utility that a consumer derives from consuming a good or service?",
            "A. Consumer Theory, B. Utility Function, C. Production Theory, D. Socioeconomic Impact",
            1, // Index of correct answer (A)
            "Easy"
        )

        insertQuizData(
            "Applied Economics",
            "What is the term used to describe the mathematical representation of a consumer's preferences for different combinations of goods and services?",
            "A. Consumer Theory, B. Utility Function, C. Production Theory, D. Socioeconomic Impact",
            1, // Index of correct answer (B)
            "Easy"
        )

        insertQuizData(
            "Applied Economics",
            "What is the term used to describe the analysis of the factors that influence the decisions of firms in the production of goods and services, including inputs, outputs, and technology?",
            "A. Consumer Theory, B. Utility Function, C. Production Theory, D. Socioeconomic Impact",
            2, // Index of correct answer (C)
            "Easy"
        )

        insertQuizData(
            "Applied Economics",
            "What is the term used to describe the movement of goods, services, and capital across international borders?",
            "A. Globalization, B. Trade, C. Capital Movement, D. Trade and Capital Movement",
            3, // Index of correct answer (D)
            "Easy"
        )

        insertQuizData(
            "Applied Economics",
            "What is the term used to describe the impact of government policies, regulations, and interventions on businesses and industries?",
            "A. Consumer Theory, B. Utility Function, C. Production Theory, D. Government Impact on Business",
            3, // Index of correct answer (D)
            "Easy"
        )

        insertQuizData(
            "Applied Economics",
            "What is the term used to describe the practice of putting money aside for future use or investment?",
            "A. Borrowing, B. Lending, C. Saving, D. Investing",
            2, // Index of correct answer (C)
            "Easy"
        )

        insertQuizData(
            "Applied Economics",
            "What is the term used to describe the act of committing money or capital to an endeavor with the expectation of obtaining an income or profit?",
            "A. Borrowing, B. Lending, C. Saving, D. Investing",
            3, // Index of correct answer (D)
            "Easy"
        )

        insertQuizData(
            "Applied Economics",
            "What is the term used to describe the economic impact of population growth on factors such as labor supply, consumption, and economic development?",
            "A. Demographic Transition, B. Population Growth Impact, C. Labor Market Dynamics, D. Migration Effects",
            1, // Index of correct answer (B)
            "Easy"
        )

        insertQuizData(
            "Applied Economics",
            "What is the term used to describe the study of the factors that influence the supply and demand of labor, as well as the determination of wages and employment levels?",
            "A. Demographic Transition, B. Population Growth Impact, C. Labor Market, D. Migration Effects",
            2, // Index of correct answer (C)
            "Easy"
        )
    }

    private fun populateMediumQuestionsECONOMICS() {
        // Add medium Business Finance quiz questions here

        insertQuizData(
            "Applied Economics",
            "Economic growth over time without considering external factors",
            "A. A measure of the total value of all final goods and services produced within a country's borders in a given time period, B. A measure of the total value of all final goods and services produced by a country's citizens, regardless of their location, C. An analysis of a country's economic development over time without considering external factors, D. An analysis of a country's economic history and past events",
            2, // Index of correct answer (C)
            "Medium"
        )

        insertQuizData(
            "Applied Economics",
            "When quantity supplied is greater than quantity demanded",
            "A. A situation where the market is in equilibrium and the quantity demanded equals the quantity supplied, B. A situation where there is a shortage in the market, and the quantity demanded exceeds the quantity supplied, C. A situation where there is a surplus in the market, and the quantity supplied exceeds the quantity demanded, D. A situation where there is a monopoly in the market, with only one seller and no close substitutes",
            2, // Index of correct answer (C)
            "Medium"
        )

        insertQuizData(
            "Applied Economics",
            "A market with a few large firms dominating",
            "A. A market structure characterized by a single seller of a unique product with no close substitutes, B. A market structure characterized by many small firms selling similar but differentiated products, C. A market structure characterized by a few large firms dominating the market and having significant market power, D. A market structure characterized by many small firms selling identical products with no ability to influence prices",
            2, // Index of correct answer (C)
            "Medium"
        )

        insertQuizData(
            "Applied Economics",
            "Analysis of a firm's competitive environment",
            "A. An analysis of a company's internal strengths and weaknesses, as well as external opportunities and threats, B. An analysis of the five competitive forces that shape the profitability of an industry, C. An analysis of the external factors that can impact a business or industry, D. An analysis of a firm's competitive environment within a particular industry",
            3, // Index of correct answer (D)
            "Medium"
        )

        insertQuizData(
            "Applied Economics",
            "Representation of consumer preferences",
            "A. The analysis of the factors that influence the decisions of consumers in the allocation of their resources, B. The mathematical representation of a consumer's preferences for different combinations of goods and services, C. The analysis of the factors that influence the decisions of firms in the production of goods and services, D. The analysis of the impact of a business or industry on the surrounding community and economy",
            1, // Index of correct answer (B)
            "Medium"
        )

        insertQuizData(
            "Applied Economics",
            "Movement of goods, services, and capital across borders",
            "A. The process of economic growth and development within a country or region, B. The study of the economic events and processes in the past, C. The impact of government policies, regulations, and interventions on businesses and industries, D. The movement of goods, services, and capital across international borders",
            3, // Index of correct answer (D)
            "Medium"
        )

        insertQuizData(
            "Applied Economics",
            "The main difference between GDP and GNP",
            "A. GDP measures the total value of goods and services produced within a country's borders, while GNP measures the total value of goods and services produced by a country's citizens, regardless of location, B. GDP measures the total income of a country's citizens, while GNP measures the total value of goods and services produced within a country's borders, C. GDP measures the total value of exports, while GNP measures the total value of imports, D. GDP and GNP are the same concepts and measure the same thing",
            0, // Index of correct answer (A)
            "Medium"
        )

        insertQuizData(
            "Applied Economics",
            "Differentiating between a shortage and a surplus",
            "A. A shortage occurs when the quantity supplied is greater than the quantity demanded, while a surplus occurs when the quantity demanded is greater than the quantity supplied, B. A shortage occurs when the quantity demanded is greater than the quantity supplied, while a surplus occurs when the quantity supplied is greater than the quantity demanded, C. A shortage and a surplus are the same concept and refer to the same market situation, D. A shortage occurs when there is a monopoly in the market, while a surplus occurs when there is perfect competition",
            1, // Index of correct answer (B)
            "Medium"
        )

        insertQuizData(
            "Applied Economics",
            "Comparing perfect competition and monopolistic competition",
            "A. In perfect competition, there are many small firms selling identical products, while in monopolistic competition, there are a few large firms dominating the market, B. In perfect competition, firms can influence prices, while in monopolistic competition, firms have no ability to influence prices, C. In perfect competition, there are many small firms selling identical products, while in monopolistic competition, there are many small firms selling similar but differentiated products, D. Perfect competition and monopolistic competition are the same market structure",
            2, // Index of correct answer (C)
            "Medium"
        )

        insertQuizData(
            "Applied Economics",
            "The main purpose of a SWOT analysis",
            "A. To analyze the five competitive forces that shape the profitability of an industry, B. To analyze a firm's competitive environment within a particular industry, C. To analyze the external factors that can impact a business or industry, D. To analyze a company's internal strengths and weaknesses, as well as external opportunities and threats",
            3, // Index of correct answer (D)
            "Medium"
        )

        insertQuizData(
            "Applied Economics",
            "Differentiating between consumer theory and production theory",
            "A. Consumer theory analyzes the factors that influence the decisions of consumers, while production theory analyzes the factors that influence the decisions of firms in production, B. Consumer theory and production theory are the same concept and analyze the same factors, C. Consumer theory analyzes the impact of a business on the surrounding community, while production theory analyzes the mathematical representation of consumer preferences, D. Consumer theory analyzes the movement of goods and services across borders, while production theory analyzes the impact of government policies on businesses",
            0, // Index of correct answer (A)
            "Medium"
        )

        insertQuizData(
            "Applied Economics",
            "Comparing the concepts of saving and investing",
            "A. Saving and investing are the same concept and refer to the act of putting money aside for future use, B. Saving refers to the act of borrowing money, while investing refers to the act of committing money or capital to an endeavor with the expectation of obtaining an income or profit, C. Saving refers to the act of putting money aside for future use, while investing refers to the act of committing money or capital to an endeavor with the expectation of obtaining an income or profit, D. Saving refers to the act of committing money or capital to an endeavor with the expectation of obtaining an income or profit, while investing refers to the act of putting money aside for future use",
            2, // Index of correct answer (C)
            "Medium"
        )

        insertQuizData(
            "Applied Economics",
            "The main difference between price elasticity and income elasticity of demand",
            "A. Price elasticity measures the responsiveness of quantity demanded to a change in price, while income elasticity measures the responsiveness of quantity demanded to a change in consumer income, B. Price elasticity measures the responsiveness of quantity supplied to a change in price, while income elasticity measures the responsiveness of quantity demanded to a change in consumer income, C. Price elasticity and income elasticity are the same concept and measure the same thing, D. Price elasticity measures the responsiveness of quantity demanded to a change in consumer income, while income elasticity measures the responsiveness of quantity demanded to a change in price",
            0, // Index of correct answer (A)
            "Medium"
        )

        insertQuizData(
            "Applied Economics",
            "Comparing the concepts of rent and minimum wage",
            "A. Rent refers to the payment made for the use of land or property, while minimum wage refers to the minimum hourly rate that employers are legally required to pay their workers, B. Rent and minimum wage are the same concept and refer to the payment made for the use of labor, C. Rent refers to the minimum hourly rate that employers are legally required to pay their workers, while minimum wage refers to the payment made for the use of land or property, D. Rent refers to the maximum hourly rate that employers are allowed to pay their workers, while minimum wage refers to the payment made for the use of capital",
            0, // Index of correct answer (A)
            "Medium"
        )

        insertQuizData(
            "Applied Economics",
            "Differentiating between monopoly and oligopoly",
            "A. In a monopoly, there are many small firms selling identical products, while in an oligopoly, there are a few large firms dominating the market, B. In a monopoly, there is a single seller of a unique product with no close substitutes, while in an oligopoly, there are a few large firms dominating the market, C. A monopoly and an oligopoly are the same market structure, D. In a monopoly, firms have no ability to influence prices, while in an oligopoly, firms can influence prices",
            1, // Index of correct answer (B)
            "Medium"
        )

        insertQuizData(
            "Applied Economics",
            "Comparing the concepts of cross elasticity and income elasticity of demand",
            "A. Cross elasticity measures the responsiveness of demand for one good to a change in the price of another good, while income elasticity measures the responsiveness of quantity demanded to a change in consumer income, B. Cross elasticity and income elasticity are the same concept and measure the same thing, C. Cross elasticity measures the responsiveness of quantity demanded to a change in price, while income elasticity measures the responsiveness of demand for one good to a change in the price of another good, D. Cross elasticity measures the responsiveness of quantity supplied to a change in price, while income elasticity measures the responsiveness of demand for one good to a change in the price of another good",
            0, // Index of correct answer (A)
            "Medium"
        )

        insertQuizData(
            "Applied Economics",
            "The main purpose of industry analysis and environmental analysis",
            "A. Industry analysis focuses on analyzing a firm's competitive environment within a particular industry, while environmental analysis focuses on analyzing the external factors that can impact a business or industry, B. Industry analysis and environmental analysis are the same concept and serve the same purpose, C. Industry analysis focuses on analyzing a company's internal strengths and weaknesses, while environmental analysis focuses on analyzing the external opportunities and threats, D. Industry analysis focuses on analyzing the five competitive forces that shape the profitability of an industry, while environmental analysis focuses on analyzing the movement of goods, services, and capital across international borders",
            0, // Index of correct answer (A)
            "Medium"
        )

        insertQuizData(
            "Applied Economics",
            "Differentiating between consumer theory and utility function",
            "A. Consumer theory analyzes the factors that influence the decisions of consumers, while utility function represents the mathematical representation of a consumer's preferences, B. Consumer theory and utility function are the same concept and analyze the same factors, C. Consumer theory analyzes the impact of a business on the surrounding community, while utility function analyzes the factors that influence the decisions of firms in production, D. Consumer theory analyzes the movement of goods and services across borders, while utility function analyzes the impact of government policies on businesses",
            0, // Index of correct answer (A)
            "Medium"
        )

        insertQuizData(
            "Applied Economics",
            "Comparing the concepts of globalization and trade and capital movement",
            "A. Globalization refers to the process of economic growth and development within a country or region, while trade and capital movement refers to the movement of goods, services, and capital across international borders, B. Globalization and trade and capital movement are the same concept and refer to the same phenomenon, C. Globalization refers to the movement of goods, services, and capital across international borders, while trade and capital movement refers to the process of economic growth and development within a country or region, D. Globalization refers to the impact of government policies, regulations, and interventions on businesses and industries, while trade and capital movement refers to the movement of goods, services, and capital across international borders",
            2, // Index of correct answer (C)
            "Medium"
        )

        insertQuizData(
            "Applied Economics",
            "The main difference between labor market and population growth impact",
            "A. The labor market analyzes the factors that influence the supply and demand of labor, while population growth impact analyzes the economic impact of population growth on factors such as labor supply, consumption, and economic development, B. The labor market and population growth impact are the same concept and analyze the same factors, C. The labor market analyzes the impact of government policies on businesses and industries, while population growth impact analyzes the movement of goods, services, and capital across international borders, D. The labor market analyzes the economic impact of population growth on factors such as labor supply, consumption, and economic development, while population growth impact analyzes the factors that influence the supply and demand of labor",
            0, // Index of correct answer (A)
            "Medium"
        )

        insertQuizData(
            "Applied Economics",
            "Comparing the concepts of socioeconomic impact and government impact on business",
            "A. Socioeconomic impact analyzes the impact of a business or industry on the surrounding community and economy, while government impact on business analyzes the impact of government policies, regulations, and interventions on businesses and industries, B. Socioeconomic impact and government impact on business are the same concept and analyze the same factors, C. Socioeconomic impact analyzes the factors that influence the decisions of consumers, while government impact on business analyzes the mathematical representation of consumer preferences, D. Socioeconomic impact analyzes the movement of goods, services, and capital across international borders, while government impact on business analyzes the factors that influence the decisions of firms in production",
            0, // Index of correct answer (A)
            "Medium"
        )

        insertQuizData(
            "Applied Economics",
            "Differentiating between Herfindahl-Hirschman Index (HHI) and SWOT analysis",
            "A. The Herfindahl-Hirschman Index (HHI) is a measure of market concentration, while a SWOT analysis is a tool for analyzing a company's internal strengths and weaknesses, as well as external opportunities and threats, B. The Herfindahl-Hirschman Index (HHI) and SWOT analysis are the same concept and serve the same purpose, C. The Herfindahl-Hirschman Index (HHI) is a tool for analyzing the five competitive forces that shape the profitability of an industry, while a SWOT analysis is a measure of market concentration, D. The Herfindahl-Hirschman Index (HHI) analyzes the impact of a business on the surrounding community, while a SWOT analysis analyzes the movement of goods, services, and capital across international borders",
            0, // Index of correct answer (A)
            "Medium"
        )

        insertQuizData(
            "Applied Economics",
            "Comparing the concepts of macroeconomics and microeconomics",
            "A. Macroeconomics analyzes the behavior of the economy as a whole, while microeconomics analyzes the behavior of individual consumers and firms, B. Macroeconomics and microeconomics are the same concept and analyze the same factors, C. Macroeconomics analyzes the impact of government policies on businesses and industries, while microeconomics analyzes the movement of goods, services, and capital across international borders, D. Macroeconomics analyzes the factors that influence the decisions of consumers, while microeconomics analyzes the mathematical representation of consumer preferences",
            0, // Index of correct answer (A)
            "Medium"
        )

        insertQuizData(
            "Applied Economics",
            "Differentiating between economic development and economic history",
            "A. Economic development analyzes the economic growth and development of a country or region over time, while economic history analyzes the economic events and processes in the past, B. Economic development and economic history are the same concept and analyze the same factors, C. Economic development analyzes the impact of government policies on businesses and industries, while economic history analyzes the movement of goods, services, and capital across international borders, D. Economic development analyzes the factors that influence the decisions of consumers, while economic history analyzes the mathematical representation of consumer preferences",
            0, // Index of correct answer (A)
            "Medium"
        )


    }

    private fun populateHardQuestionsECONOMICS() {
        // Add hard Business Finance quiz questions here

        insertQuizData(
            "Applied Economics",
            "A company produces a product with a price elasticity of demand of -2.5. If the company wants to increase its total revenue, should it increase or decrease the price of the product?",
            "A. Increase price, B. Decrease price, C. Increase price as demand is inelastic, D. Decrease price as demand is inelastic",
            1, // Index of correct answer (B)
            "Hard"
        )

        insertQuizData(
            "Applied Economics",
            "A company is considering entering a new market. The Herfindahl-Hirschman Index (HHI) for this market is 2,500. What can you say about the level of market concentration in this industry?",
            "A. Highly concentrated, B. Moderately concentrated, C. Unconcentrated, D. Cannot determine from HHI alone",
            0, // Index of correct answer (A)
            "Hard"
        )

        insertQuizData(
            "Applied Economics",
            "A country has a GDP of 500 billion pesos and a GNP of 550 billion pesos. What can you infer about the country's net income from abroad?",
            "A. The country has a net income from abroad of 50 billion pesos, B. The country has a net income from abroad of -50 billion pesos, C. The country has no net income from abroad, since GDP and GNP are equal, D. It is impossible to determine the country's net income from abroad based on the provided information",
            0, // Index of correct answer (A)
            "Hard"
        )


        insertQuizData(
            "Applied Economics",
            "A company in the Philippines is considering raising the price of its product from ₱100 to ₱120. If the price elasticity of demand for the product is -1.5, what will be the effect on the company's total revenue?",
            "A. Total revenue will increase, B. Total revenue will decrease, C. Total revenue will remain the same, D. It is impossible to determine the effect on total revenue without additional information",
            0, // Index of correct answer (A)
            "Hard"
        )

        insertQuizData(
            "Applied Economics",
            "The Philippine government is considering implementing a minimum wage of ₱500 per day for all workers. If a firm currently employs 50 workers at a wage of ₱400 per day, and its total revenue is ₱100,000 per day, what is the maximum daily wage the firm can afford to pay without incurring losses?",
            "A. ₱2,000 per day, B. ₱1,500 per day, C. ₱1,000 per day, D. ₱500 per day",
            1, // Index of correct answer (B)
            "Hard"
        )

        insertQuizData(
            "Applied Economics",
            "A Philippine firm is considering entering a new market with a Herfindahl-Hirschman Index (HHI) of 1,800. The firm estimates that its market share in this industry will be 15%. What will be the new HHI after the firm enters the market?",
            "A. 2,025, B. 1,977.5, C. 1,950, D. 1,922.5",
            0, // Index of correct answer (A)
            "Hard"
        )

        insertQuizData(
            "Applied Economics",
            "The Philippine government is considering imposing a tariff on imported goods to protect domestic industries. If the tariff increases the price of imported goods by 20%, and the price elasticity of demand for imported goods is -1.2, what will be the effect on the total expenditure on imported goods?",
            "A. Total expenditure on imported goods will increase, B. Total expenditure on imported goods will decrease, C. Total expenditure on imported goods will remain the same, D. It is impossible to determine the effect on total expenditure without additional information",
            1, // Index of correct answer (B)
            "Hard"
        )

        insertQuizData(
            "Applied Economics",
            "A Philippine firm is considering expanding its operations to a new location. The firm estimates that its fixed costs will increase by ₱500,000, and its variable costs will increase by ₱20 per unit produced. If the firm currently produces 10,000 units and sells them at ₱50 per unit, what is the minimum price the firm should charge to break even after the expansion?",
            "A. ₱52 per unit, B. ₱55 per unit, C. ₱60 per unit, D. ₱65 per unit",
            2, // Index of correct answer (C)
            "Hard"
        )

        insertQuizData(
            "Applied Economics",
            "The Philippine government is considering implementing a tax on carbon emissions to address environmental concerns. If the tax increases the cost of production for a firm by ₱2 per unit, and the firm currently produces 100,000 units at a price of ₱10 per unit, what is the maximum tax rate the firm can afford without incurring losses?",
            "A. 5%, B. 10%, C. 15%, D. 20%",
            3, // Index of correct answer (D)
            "Hard"
        )

        insertQuizData(
            "Applied Economics",
            "A Philippine firm is considering investing in a new production technology that will increase its fixed costs by ₱1,000,000 but reduce its variable costs by ₱5 per unit produced. If the firm currently produces 200,000 units and sells them at ₱25 per unit, what is the minimum level of output the firm must achieve to justify the investment?",
            "A. 250,000 units, B. 300,000 units, C. 350,000 units, D. 400,000 units",
            3, // Index of correct answer (D)
            "Hard"
        )

        insertQuizData(
            "Applied Economics",
            "The Philippine government is considering implementing a subsidy for domestic agricultural producers to promote food security. If the subsidy reduces the cost of production by ₱2 per unit, and the price elasticity of supply for agricultural products is 0.8, what will be the effect on the total quantity supplied?",
            "A. Total quantity supplied will increase, B. Total quantity supplied will decrease, C. Total quantity supplied will remain the same, D. It is impossible to determine the effect on total quantity supplied without additional information",
            0, // Index of correct answer (A)
            "Hard"
        )

        insertQuizData(
            "Applied Economics",
            "A Philippine firm is considering entering a new market with three existing competitors. The market shares of the existing competitors are 40%, 35%, and 25%. If the firm estimates that it can capture a 20% market share, what will be the new Herfindahl-Hirschman Index (HHI) for the market?",
            "A. 2,500, B. 2,700, C. 2,900, D. 3,100",
            2, // Index of correct answer (C)
            "Hard"
        )

        insertQuizData(
            "Applied Economics",
            "The Philippine government is considering implementing a value-added tax (VAT) on certain goods and services. If the VAT rate is 12%, and the price elasticity of demand for the affected goods and services is -0.7, what will be the effect on the total revenue from the VAT?",
            "A. Total revenue from the VAT will increase, B. Total revenue from the VAT will decrease, C. Total revenue from the VAT will remain the same, D. It is impossible to determine the effect on total revenue without additional information",
            0, // Index of correct answer (A)
            "Hard"
        )

        insertQuizData(
            "Applied Economics",
            "A Philippine firm is considering outsourcing part of its production to a foreign country with lower labor costs. If the firm's current labor cost is ₱500 per unit and the outsourcing option would reduce labor costs by 40%, what is the maximum fixed cost the firm can afford to pay for outsourcing without increasing its total cost?",
            "A. ₱100,000, B. ₱150,000, C. ₱200,000, D. ₱250,000",
            2, // Index of correct answer (C)
            "Hard"
        )

        insertQuizData(
            "Applied Economics",
            "The Philippine government is considering implementing a tax on sugary beverages to promote healthier lifestyles. If the tax increases the price of sugary beverages by 25%, and the price elasticity of demand for sugary beverages is -1.8, what will be the effect on the total revenue from the tax?",
            "A. Total revenue from the tax will increase, B. Total revenue from the tax will decrease, C. Total revenue from the tax will remain the same, D. It is impossible to determine the effect on total revenue without additional information",
            1, // Index of correct answer (B)
            "Hard"
        )

        insertQuizData(
            "Applied Economics",
            "A Philippine firm is considering investing in a new marketing campaign to increase the demand for its product. If the firm's current revenue is ₱2,000,000, and the marketing campaign is expected to increase demand by 20% with a price elasticity of demand of -1.2, what is the maximum amount the firm should spend on the marketing campaign to ensure a positive return on investment?",
            "A. ₱200,000, B. ₱300,000, C. ₱400,000, D. ₱500,000",
            2, // Index of correct answer (C)
            "Hard"
        )

        insertQuizData(
            "Applied Economics",
            "The Philippine government is considering implementing a tax on luxury goods to promote income redistribution. If the tax increases the price of luxury goods by 30%, and the price elasticity of demand for luxury goods is -0.5, what will be the effect on the total expenditure on luxury goods?",
            "A. Total expenditure on luxury goods will increase, B. Total expenditure on luxury goods will decrease, C. Total expenditure on luxury goods will remain the same, D. It is impossible to determine the effect on total expenditure without additional information",
            0, // Index of correct answer (A)
            "Hard"
        )

        insertQuizData(
            "Applied Economics",
            "A Philippine firm is considering investing in a new production technology that will reduce its variable costs by ₱10 per unit produced. If the firm currently produces 100,000 units and sells them at ₱50 per unit, what is the minimum price the firm should charge after implementing the new technology to ensure a 20% increase in profit?",
            "A. ₱45 per unit, B. ₱50 per unit, C. ₱55 per unit, D. ₱60 per unit",
            3, // Index of correct answer (C)
            "Hard"
        )

        insertQuizData(
            "Applied Economics",
            "The Philippine government is considering implementing a carbon tax to reduce greenhouse gas emissions. If the tax increases the cost of production for a firm by ₱5 per unit, and the firm currently produces 50,000 units at a price of ₱30 per unit, what is the maximum tax rate the firm can afford without incurring losses?",
            "A. 5%, B. 10%, C. 15%, D. 20%",
            1, // Index of correct answer (B)
            "Hard"
        )

        insertQuizData(
            "Applied Economics",
            "A Philippine firm is considering entering a new market with a Herfindahl-Hirschman Index (HHI) of 2,200. The firm estimates that its market share in this industry will be 25%. What will be the new HHI after the firm enters the market?",
            "A. 2,425, B. 2,475, C. 2,525, D. 2,575",
            1, // Index of correct answer (B)
            "Hard"
        )

        insertQuizData(
            "Applied Economics",
            "The Philippine government is considering implementing a subsidy for domestic renewable energy producers to promote sustainable development. If the subsidy reduces the cost of production by ₱3 per unit, and the price elasticity of supply for renewable energy is 1.2, what will be the effect on the total quantity supplied?",
            "A. Total quantity supplied will increase, B. Total quantity supplied will decrease, C. Total quantity supplied will remain the same, D. It is impossible to determine the effect on total quantity supplied without additional information",
            0, // Index of correct answer (A)
            "Hard"
        )

        insertQuizData(
            "Applied Economics",
            "A Philippine firm is considering outsourcing part of its production to a foreign country with lower labor costs. If the firm's current labor cost is ₱800 per unit and the outsourcing option would reduce labor costs by 30%, what is the minimum price the firm should charge after outsourcing to maintain its current profit level?",
            "A. ₱560 per unit, B. ₱600 per unit, C. ₱640 per unit, D. ₱680 per unit",
            0, // Index of correct answer (A)
            "Hard"
        )

        insertQuizData(
            "Applied Economics",
            "The Philippine government is considering implementing a tax on plastic bags to reduce waste and promote environmental sustainability. If the tax increases the price of plastic bags by 50%, and the price elasticity of demand for plastic bags is -0.8, what will be the effect on the total revenue from the tax?",
            "A. Total revenue from the tax will increase, B. Total revenue from the tax will decrease, C. Total revenue from the tax will remain the same, D. It is impossible to determine the effect on total revenue without additional information",
            0, // Index of correct answer (A)
            "Hard"
        )

        insertQuizData(
            "Applied Economics",
            "A Philippine firm is considering investing in a new marketing campaign to increase the demand for its product. If the firm's current revenue is ₱3,000,000, and the marketing campaign is expected to increase demand by 15% with a price elasticity of demand of -1.5, what is the maximum amount the firm should spend on the marketing campaign to ensure a positive return on investment?",
            "A. ₱300,000, B. ₱400,000, C. ₱500,000, D. ₱600,000",
            1, // Index of correct answer (B)
            "Hard"
        )

        insertQuizData(
            "Applied Economics",
            "The Philippine government is considering implementing a tax on cigarettes to discourage smoking and promote public health. If the tax increases the price of cigarettes by 40%, and the price elasticity of demand for cigarettes is -0.6, what will be the effect on the total expenditure on cigarettes?",
            "A. Total expenditure on cigarettes will increase, B. Total expenditure on cigarettes will decrease, C. Total expenditure on cigarettes will remain the same, D. It is impossible to determine the effect on total expenditure without additional information",
            0, // Index of correct answer (A)
            "Hard"
        )

        insertQuizData(
            "Applied Economics",
            "A Philippine firm is considering investing in a new production technology that will increase its fixed costs by ₱2,000,000 but reduce its variable costs by ₱15 per unit produced. If the firm currently produces 150,000 units and sells them at ₱80 per unit, what is the minimum level of output the firm must achieve to justify the investment?",
            "A. 200,000 units, B. 250,000 units, C. 300,000 units, D. 350,000 units",
            2, // Index of correct answer (C)
            "Hard"
        )

        insertQuizData(
            "Applied Economics",
            "The Philippine government is considering implementing a tax on alcohol to reduce alcohol consumption and related social costs. If the tax increases the price of alcohol by 20%, and the price elasticity of demand for alcohol is -0.9, what will be the effect on the total revenue from the tax?",
            "A. Total revenue from the tax will increase, B. Total revenue from the tax will decrease, C. Total revenue from the tax will remain the same, D. It is impossible to determine the effect on total revenue without additional information",
            0, // Index of correct answer (A)
            "Hard"
        )

        insertQuizData(
            "Applied Economics",
            "A Philippine firm is considering outsourcing part of its production to a foreign country with lower labor costs. If the firm's current labor cost is ₱1,000 per unit and the outsourcing option would reduce labor costs by 50%, what is the maximum fixed cost the firm can afford to pay for outsourcing without increasing its total cost?",
            "A. ₱250,000, B. ₱500,000, C. ₱750,000, D. ₱1,000,000",
            2, // Index of correct answer (C)
            "Hard"
        )

        insertQuizData(
            "Applied Economics",
            "The Philippine government is considering implementing a tax on gasoline to reduce carbon emissions and promote the use of public transportation. If the tax increases the price of gasoline by 15%, and the price elasticity of demand for gasoline is -0.4, what will be the effect on the total expenditure on gasoline?",
            "A. Total expenditure on gasoline will increase, B. Total expenditure on gasoline will decrease, C. Total expenditure on gasoline will remain the same, D. It is impossible to determine the effect on total expenditure without additional information",
            0, // Index of correct answer (A)
            "Hard"
        )

        insertQuizData(
            "Applied Economics",
            "A Philippine firm is considering investing in a new marketing campaign to increase the demand for its product. If the firm's current revenue is ₱5,000,000, and the marketing campaign is expected to increase demand by 25% with a price elasticity of demand of -1.8, what is the maximum amount the firm should spend on the marketing campaign to ensure a positive return on investment?",
            "A. ₱500,000, B. ₱750,000, C. ₱1,000,000, D. ₱1,250,000",
            2, // Index of correct answer (C)
            "Hard"
        )

    }
}