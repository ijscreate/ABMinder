package com.mtcdb.stem.mathtrix.dictionary.database

import android.content.*
import android.database.sqlite.*

class DictionaryDataInsertion(
    val context : Context,
    private val databaseHelper : DictionaryDatabaseHelper,
) {

    fun insert() {
        insertTermsIntoDatabase()
        databaseHelper.deleteDuplicateTerms()
    }

    private fun insertTermsIntoDatabase() {

        val db = databaseHelper.writableDatabase

        //checks if the terms are already inserted
        val isDatabasePopulated = checkIfDatabaseIsPopulated(db)

        if (!isDatabasePopulated) {
            db.beginTransaction()
            try {
                val termsToInsert = listOf(
                    Triple(
                        "Asset",
                        "Anything of value owned by a person or company, which can be converted into cash or provide future economic benefits.",
                        "Fixed assets (e.g., land, buildings); Current assets (e.g., cash, inventory)"
                    ),
                    Triple(
                        "Audit",
                        "A systematic examination of financial records, statements, or operations to ensure accuracy, compliance, and adherence to regulations.",
                        "Internal audit (conducted by the company's own auditors to assess internal controls); External audit (performed by independent auditors for regulatory compliance)"
                    ),
                    Triple(
                        "Annual Report",
                        "A comprehensive report by a company, providing financial and operational information to shareholders and the public, typically published on an annual basis.",
                        "Financial statements (e.g., balance sheet, income statement) summarizing financial performance; Shareholder report detailing company achievements and future goals"
                    ),
                    Triple(
                        "Angel Investor",
                        "An individual who provides capital for a business startup in exchange for ownership equity, mentorship, or other involvement.",
                        "Startup funding (providing initial capital for a tech startup); Seed capital (investing in early-stage ventures with high growth potential)"
                    ),
                    Triple(
                        "Accounts Payable",
                        "The amount of money a company owes to its suppliers or creditors for goods or services received.",
                        "Payment terms negotiated with suppliers for raw materials; Outstanding invoices awaiting payment for services rendered"
                    ),
                    Triple(
                        "Accrual Accounting",
                        "An accounting method where revenue and expenses are recorded when they are earned or incurred, not when cash is exchanged.",
                        "Recognizing revenue when goods are delivered, regardless of payment timing; Recording expenses when incurred, even if payment is delayed"
                    ),
                    Triple(
                        "Advertising",
                        "The promotion of a product, service, or brand to attract and persuade customers.",
                        "Marketing strategy (utilizing social media, TV ads); Ad campaign promoting a new product launch"
                    ),
                    Triple(
                        "Agile Methodology",
                        "A project management and product development approach that prioritizes flexibility and collaboration.",
                        "Scrum (iterative development process); Sprint planning (short development cycles for rapid product iteration)"
                    ),
                    Triple(
                        "Asset Turnover Ratio",
                        "A financial ratio that measures a company's ability to generate revenue from its assets.",
                        "Asset efficiency (how efficiently assets are utilized to generate sales); Sales turnover (speed at which inventory is sold)"
                    ),
                    Triple(
                        "Accounts Receivable Turnover",
                        "A ratio that measures how efficiently a company collects payments on its receivables.",
                        "Days sales outstanding (average number of days to collect receivables); Receivables management (strategies to improve collection efficiency)"
                    ),
                    Triple(
                        "Affiliate Marketing",
                        "A performance-based marketing strategy where a business rewards affiliates for bringing in customers or traffic through the affiliate's marketing efforts.",
                        "Affiliate network (platform facilitating affiliate partnerships); Commission-based marketing (compensating affiliates based on performance)"
                    ),
                    Triple(
                        "Antitrust Laws",
                        "Laws designed to promote fair competition and prevent monopolies or other anticompetitive business practices.",
                        "Competition law (regulating market competition); Regulatory compliance (ensuring adherence to antitrust regulations)"
                    ),
                    Triple(
                        "Asset Allocation",
                        "The strategic distribution of a portfolio's investments among various asset classes to optimize risk and return.",
                        "Diversification (reducing risk by investing in different asset types); Investment strategy (allocating funds based on risk tolerance)"
                    ),
                    Triple(
                        "Annual Percentage Rate (APR)",
                        "The annualized interest rate that considers additional fees and costs associated with a loan or financial product.",
                        "Examples: Loan cost calculation (estimating total borrowing expenses); Interest rate (cost of borrowing expressed annually)"
                    ),
                    Triple(
                        "Accounts Receivable Financing",
                        "A form of financing where a company uses its receivables as collateral to obtain a loan.",
                        "Factoring (selling receivables to a third party for immediate cash); Working capital financing (using receivables to secure short-term funding)"
                    ),
                    Triple(
                        "Arbitrage",
                        "The practice of taking advantage of price differences in different markets to make a profit.",
                        "Risk-free profit (exploiting market inefficiencies for financial gain); Market inefficiency (discrepancies in asset prices across markets)"
                    ),
                    Triple(
                        "Brand Equity",
                        "The perceived value or strength of a brand based on consumer perception and brand loyalty.",
                        "Brand recognition (awareness and familiarity with a brand); Brand building (activities aimed at enhancing brand value)"
                    ),
                    Triple(
                        "Break-Even Point",
                        "The level of sales at which a business covers its total costs and neither makes a profit nor incurs a loss.",
                        "Cost-volume-profit analysis (evaluating profitability at different sales levels); Breakeven analysis (determining breakeven sales volume)"
                    ),
                    Triple(
                        "Business Intelligence (BI)",
                        "The use of data analysis tools and techniques to make informed business decisions.",
                        "Data analytics (processing and interpreting business data); Decision support (using insights for strategic planning)"
                    ),
                    Triple(
                        "Balance Sheet",
                        "A financial statement that provides a snapshot of a company's financial position, showing assets, liabilities, and equity.",
                        "Financial statement (documenting financial status); Accounting (recording and reporting financial transactions)"
                    ),
                    Triple(
                        "Benchmarking",
                        "The process of comparing a company's performance metrics with those of industry leaders or competitors.",
                        "Performance measurement (evaluating company performance); Competitive analysis (assessing market position)"
                    ),
                    Triple(
                        "Back-End",
                        "The technical infrastructure of a software application, responsible for server-side processing and data storage.",
                        "Front-end vs. back-end (user interface vs. server logic); Web development (building and maintaining software systems)"
                    ),
                    Triple(
                        "Big Data",
                        "Large and complex sets of data that require advanced tools and technologies for analysis and processing.",
                        "Data analytics (extracting insights from large datasets); Data science (applying statistical techniques to big data)"
                    ),
                    Triple(
                        "Brainstorming",
                        "A creative problem-solving technique where a group generates ideas and solutions through open and spontaneous discussion.",
                        "Idea generation (sparking creativity); Team collaboration (working together to solve challenges)"
                    ),
                    Triple(
                        "Bull Market",
                        "A financial market characterized by rising prices and optimism among investors.",
                        "Stock market trends (direction of market movements); Market conditions (factors influencing investor sentiment)"
                    ),
                    Triple(
                        "Business Model",
                        "The plan or framework that outlines how a company creates, delivers, and captures value.",
                        "Revenue streams (sources of income); Monetization strategy (methods for generating revenue)"
                    ),
                    Triple(
                        "Buyer Persona",
                        "A detailed and semi-fictional representation of a company's ideal customer, based on market research and data.",
                        "Marketing strategy (targeting specific customer segments); Customer profiling (understanding buyer behaviors)"
                    ),
                    Triple(
                        "Blue Ocean Strategy",
                        "A business approach that focuses on creating and capturing new market space, rather than competing in existing markets.",
                        "Innovation strategy (pioneering new market opportunities); Market differentiation (creating unique value propositions)"
                    ),
                    Triple(
                        "Budget Variance",
                        "The difference between the planned or budgeted amount and the actual amount spent or earned.",
                        "Financial planning (allocating resources); Budget analysis (evaluating budget performance)"
                    ),
                    Triple(
                        "Burn Rate",
                        "The rate at which a company uses its available cash or capital, often expressed as a monthly or weekly expenditure.",
                        "Cash burn (rate of cash depletion); Runway (duration until funds are exhausted)"
                    ),
                    Triple(
                        "Brand Positioning",
                        "The way a brand is perceived in the minds of consumers relative to competitors.",
                        "Brand identity (unique brand characteristics); Market positioning (strategic brand placement)"
                    ),
                    Triple(
                        "Business Process Reengineering (BPR)",
                        "The redesign and optimization of business processes to achieve improvements in efficiency, quality, and performance.",
                        "Examples: Process improvement (enhancing operational workflows); Organizational change (transforming business practices)"
                    ),
                    Triple(
                        "B2B (Business-to-Business)",
                        "Commerce transactions between businesses, where products or services are sold from one business to another.",
                        "B2B marketing (targeting corporate clients); Corporate sales (business-to-business transactions)"
                    ),
                    Triple(
                        "B2C (Business-to-Consumer)",
                        "Commerce transactions between a business and individual consumers, involving the sale of products or services.",
                        "B2C e-commerce (online retailing); Retail marketing (promoting products to individual shoppers)"
                    ),
                    Triple(
                        "Blockchain",
                        "A decentralized and distributed digital ledger technology used to record transactions across multiple computers.",
                        "Cryptocurrency (digital currencies using blockchain); Smart contracts (self-executing contracts on blockchain)"
                    ),
                    Triple(
                        "Bootstrapping",
                        "The process of building or starting a business with minimal external capital or resources.",
                        "Self-funding (using personal savings for startup); Lean startup (building a business with limited resources)"
                    ),
                    Triple(
                        "Bill of Materials (BOM)",
                        "A comprehensive list of materials, components, and assemblies required to manufacture a product.",
                        "Manufacturing (producing goods); Production planning (scheduling and managing manufacturing processes)"
                    ),
                    Triple(
                        "Brand Ambassador",
                        "An individual or influencer who represents and promotes a brand to increase awareness and attract customers.",
                        "Brand advocacy (endorsing products on social media); Influencer marketing (leveraging influencers to reach target audience)"
                    ),
                    Triple(
                        "Business Continuity Planning",
                        "The process of creating systems and procedures to ensure a company can operate and recover from disruptions or disasters.",
                        "Disaster recovery (resuming operations after a crisis); Risk management (mitigating potential threats)"
                    ),
                    Triple(
                        "Beta Testing",
                        "The second phase of software testing, conducted by a select group of external users before the official release.",
                        "User acceptance testing (evaluating product usability); Quality assurance (ensuring product reliability)"
                    ),
                    Triple(
                        "Cash Conversion Cycle (CCC)",
                        "The time it takes for a company to convert its investments in inventory and other resources into cash flow from sales.",
                        "Working capital management (optimizing cash flow); Inventory turnover (speed of inventory conversion)"
                    ),
                    Triple(
                        "Corporate Social Responsibility (CSR)",
                        "A company's commitment to ethical and responsible business practices that contribute to social and environmental well-being.",
                        "Sustainability (environmental conservation initiatives); Social impact (community development projects)"
                    ),
                    Triple(
                        "Cost of Goods Sold (COGS)",
                        "The direct costs incurred in producing a product or delivering a service, including materials and labor.",
                        "COGS formula (calculation method); Gross profit (revenue minus COGS)"
                    ),
                    Triple(
                        "Crowdfunding",
                        "A fundraising method where individuals contribute small amounts of money to support a project or venture.",
                        "Kickstarter (popular crowdfunding platform); Crowdfunding platforms (online portals for fundraising)"
                    ),
                    Triple(
                        "Customer Acquisition Cost (CAC)",
                        "The total cost a company incurs to acquire a new customer, including marketing and sales expenses.",
                        "Customer retention (efforts to retain existing customers); CAC calculation (determining cost-effectiveness of acquisition)"
                    ),
                    Triple(
                        "Competitive Advantage",
                        "A unique strength or attribute that gives a company a significant advantage over its competitors.",
                        "Market differentiation (setting oneself apart from competitors); Strategic positioning (strategically positioning products in the market)"
                    ),
                    Triple(
                        "Customer Relationship Management (CRM)",
                        "A technology and strategy for managing a company's interactions with current and potential customers.",
                        "CRM software (tools for managing customer relationships); Customer loyalty (building and maintaining customer trust)"
                    ),
                    Triple(
                        "Core Competency",
                        "A specific area of expertise or capability that gives a company a competitive advantage in its industry.",
                        "Strategic management (managing core competencies); Competitive strategy (leveraging core competencies for competitive advantage)"
                    ),
                    Triple(
                        "Collaborative Economy",
                        "A business model where individuals share resources, services, or products through digital platforms.",
                        "Sharing economy (peer-to-peer resource sharing); Peer-to-peer (direct exchange between individuals)"
                    ),
                    Triple(
                        "Capital Expenditure (CapEx)",
                        "Spending on assets that will provide long-term benefits, such as property, equipment, or infrastructure.",
                        "CapEx vs. OpEx (capital vs. operating expenses); Capital budgeting (allocating funds for long-term investments)"
                    ),
                    Triple(
                        "Cost Leadership",
                        "A competitive strategy where a company aims to become the lowest-cost producer in its industry.",
                        "Cost advantage (lower costs compared to competitors); Economies of scale (cost advantages from increased production)"
                    ),
                    Triple(
                        "Convertible Bond",
                        "A type of bond that can be converted into a predetermined number of shares of the issuing company's stock.",
                        "Debt financing (raising funds through borrowing); Financial instruments (securities used for investment)"
                    ),
                    Triple(
                        "Cross-Selling",
                        "The strategy of selling additional products or services to existing customers, often related to their initial purchase.",
                        "Upselling (encouraging customers to buy higher-value items); Sales strategy (tactics for increasing revenue)"
                    ),
                    Triple(
                        "Critical Path Method (CPM)",
                        "A project management technique that identifies the sequence of tasks with the longest duration, determining the project's critical path.",
                        "Project scheduling (timelines for completing tasks); Task dependency (sequence of activities)"
                    ),
                    Triple(
                        "Crisis Management",
                        "The process of preparing for and responding to a crisis or emergency situation to minimize damage and ensure recovery.",
                        "Risk mitigation (reducing potential negative impacts); Emergency response (actions taken during crises)"
                    ),
                    Triple(
                        "Consumer Behavior",
                        "The study of how individuals and groups make decisions to satisfy their needs and wants as consumers.",
                        "Market research (collecting data on consumer preferences); Buying habits (patterns of consumer purchasing)"
                    ),
                    Triple(
                        "Cross-Functional Team",
                        "A team that includes members from different departments or functions within an organization, working together on a common goal.",
                        "Team collaboration (working together towards shared objectives); Interdisciplinary (integrating multiple disciplines)"
                    ),
                    Triple(
                        "Cost-Benefit Analysis",
                        "A systematic process for assessing the pros and cons of an action, decision, or project, comparing costs and expected benefits.",
                        "Decision-making (choosing between alternatives); Project evaluation (assessing project feasibility)"
                    ),
                    Triple(
                        "Corporate Governance",
                        "The system of rules, practices, and processes by which a company is directed and controlled, involving relationships among stakeholders.",
                        "Board of directors (governing body overseeing company operations); Ethical standards (guidelines for corporate conduct)"
                    ),
                    Triple(
                        "Customer Segmentation",
                        "The process of dividing a market into distinct groups of buyers with similar characteristics or needs.",
                        "Target audience (specific customer groups); Marketing strategy (tailoring offerings to segmented markets)"
                    ),
                    Triple(
                        "Crowdsourcing",
                        "The practice of obtaining ideas, content, or services by soliciting contributions from a large group of people, typically online.",
                        "Open innovation (collaborative idea generation); Collective intelligence (tapping into collective knowledge)"
                    ),
                    Triple(
                        "Centralized Organization",
                        "An organizational structure where decision-making authority is concentrated at the top levels of management.",
                        "Hierarchy (organizational structure); Organizational design (structuring decision-making processes)"
                    ),
                    Triple(
                        "Customer Lifetime Value (CLV)",
                        "The predicted total revenue a company expects to earn from a customer throughout their entire relationship.",
                        "Retention strategy (efforts to retain customers); CLV calculation (predicting long-term customer value)"
                    ),
                    Triple(
                        "Cost of Capital",
                        "The required rate of return that a company must achieve on its investments to maintain or increase its stock price.",
                        "WACC (weighted average cost of capital); Capital budgeting (allocating funds for investment opportunities)"
                    ),
                    Triple(
                        "Continuous Improvement",
                        "An ongoing effort to improve products, services, or processes through incremental and iterative changes.",
                        "Kaizen (Japanese concept of continuous improvement); Process optimization (enhancing operational efficiency)"
                    ),
                    Triple(
                        "Corporate Culture",
                        "The shared values, beliefs, and practices that shape the behavior and attitudes of individuals within an organization.",
                        "Organizational culture (company values and norms); Company values (core beliefs guiding behavior)"
                    ),
                    Triple(
                        "Competitive Intelligence",
                        "The process of gathering, analyzing, and using information about competitors and the competitive environment.",
                        "Market research (collecting competitor data); Business strategy (using competitor insights to inform strategy)"
                    ),
                    Triple(
                        "Cash Reserve Ratio",
                        "The percentage of a bank's deposits that must be held in reserve, not available for lending or investment.",
                        "Monetary policy (regulating money supply); Banking regulation (government rules for financial institutions)"
                    ),
                    Triple(
                        "Customer Churn",
                        "The rate at which customers stop doing business with a company over a given period.",
                        "Churn rate (percentage of customers lost); Customer retention (strategies to retain customers)"
                    ),
                    Triple(
                        "Cross-Training",
                        "The practice of training employees to perform tasks or responsibilities outside of their primary roles, increasing versatility.",
                        "Skill development (broadening employee capabilities); Employee training (ongoing professional development)"
                    ),
                    Triple(
                        "Cost-plus Pricing",
                        "A pricing strategy where a company determines the cost of producing a product and adds a markup to establish the selling price.",
                        "Markup (profit margin added to cost); Pricing strategy (setting product prices)"
                    ),
                    Triple(
                        "Cloud Computing",
                        "The delivery of computing services, including storage, processing power, and software, over the internet.",
                        "Infrastructure as a Service (IaaS) (cloud-based infrastructure); SaaS (Software as a Service) (cloud-based software)"
                    ),
                    Triple(
                        "Competitive Analysis",
                        "The process of evaluating and comparing a company's strengths and weaknesses against those of its competitors.",
                        "SWOT analysis (assessing strengths, weaknesses, opportunities, and threats); Market positioning (strategic differentiation)"
                    ),
                    Triple(
                        "Time Series Analysis",
                        "A statistical method that involves analyzing and interpreting data points collected over successive intervals of time to identify patterns or trends.",
                        "Analyzing monthly sales data for a retail business over the past three years to identify seasonal patterns and trends."
                    ),
                    Triple(
                        "Capital Gains",
                        "The profit realized from the sale of an asset, such as stocks or real estate, calculated as the difference between the selling price and the purchase price.",
                        "If you purchase 100 shares of a company's stock at $50 per share and sell them a year later at $65 per share, your capital gain is $15 per share"
                    ),
                    Triple(
                        "Dividend Yield",
                        "A financial ratio calculated by dividing the annual dividend payment by the stock's current market price, expressing the yield as a percentage.",
                        "If a stock is trading at $100 per share and pays an annual dividend of $5 per share, the dividend yield is 5% ($5/$100)."
                    ),
                    Triple(
                        "Cost of Equity",
                        "The return a company is expected to provide to its equity investors, often calculated using the Capital Asset Pricing Model (CAPM).",
                        "Using the Capital Asset Pricing Model (CAPM) to estimate the cost of equity, where the risk-free rate is 3%, the market risk premium is 7%, and the company's beta is 1.2, the cost of equity would be 11.4%."
                    ),
                    Triple(
                        "Default Risk",
                        "The risk that a borrower may fail to meet their debt obligations, leading to a potential loss for the lender.",
                        "A lender assessing a borrower's creditworthiness to determine the level of default risk before issuing a loan."
                    ),
                    Triple(
                        "Discounted Cash Flow (DCF)",
                        "A valuation method that estimates the present value of a future stream of cash flows, commonly used in investment analysis.",
                        "Using DCF to evaluate the potential return of an investment project by discounting its expected future cash flows."
                    ),
                    Triple(
                        "Operating Cycle",
                        "The time it takes for a company to convert its investments in raw materials, production, and receivables into cash through sales.",
                        "Calculating a company's operating cycle to understand its cash flow dynamics and working capital needs."
                    ),
                    Triple(
                        "Futures Contract",
                        "A standardized financial contract obligating the buyer to purchase, or the seller to sell, an underlying asset at a specified future date and agreed-upon price.",
                        "A farmer enters into a futures contract to sell 1,000 bushels of wheat at a price of $5 per bushel, with delivery scheduled in three months."
                    ),
                    Triple(
                        "Return on Assets (ROA)",
                        "A financial ratio that measures a company's efficiency in utilizing its assets to generate profits.",
                        "If a company has a net income of $500,000 and total assets of $5 million, the ROA would be 10% ($500,000/$5,000,000)."
                    ),
                    Triple(
                        "Scenario Analysis",
                        "An analytical technique that examines various possible future events and their potential impact on a business or investment.",
                        "Assessing the potential financial impact of different economic scenarios (e.g., recession, growth) on a company's sales and profitability."
                    ),
                    Triple(
                        "Working Capital Turnover",
                        "A financial ratio that measures how efficiently a company uses its working capital to generate sales.",
                        "If a company has an annual revenue of $2 million and an average working capital of $500,000, the working capital turnover ratio would be 4 ($2,000,000/$500,000), indicating the company generates $4 in sales for every dollar of working capital"
                    ),
                    Triple(
                        "Net Present Value (NPV)",
                        "The present value of all future cash inflows and outflows associated with an investment, used to assess its profitability.",
                        "Calculating the NPV of a project to determine if it's expected to generate positive returns."
                    ),
                    Triple(
                        "Internal Rate of Return (IRR)",
                        "The discount rate at which the NPV of an investment is 0, commonly used to compare investment options.",
                        "Comparing the IRRs of different investment projects to select the most profitable one."
                    ),
                    Triple(
                        "Payback Period",
                        "The time it takes for an investment to recover its initial cost.",
                        "Calculating the payback period to estimate how quickly an investment will generate returns."
                    ),
                    Triple(
                        "Marginal Cost",
                        "The additional cost of producing one more unit of a product or service.",
                        "Calculating the marginal cost to determine the optimal production level."
                    ),
                    Triple(
                        "Break-Even Point",
                        "The point at which total revenue equals total cost, resulting in no profit or loss.",
                        "Determining the break-even point to understand the minimum sales volume required to cover costs."
                    ),
                    Triple(
                        "Contribution Margin",
                        "The amount of revenue that remains after variable costs are covered, contributing to fixed costs and profit.",
                        "Analyzing the contribution margin to evaluate product profitability and make pricing decisions."
                    ),
                    Triple(
                        "Budget Variance Analysis",
                        "Comparing actual results to budgeted amounts to identify deviations and understand performance drivers.",
                        "Performing budget variance analysis to track financial performance and identify areas for improvement."
                    ),
                    Triple(
                        "Economic Order Quantity (EOQ)",
                        "The optimal quantity of a product to order at a time to minimize total ordering and holding costs.",
                        "Calculating the EOQ to optimize inventory levels and reduce costs."
                    ),
                    Triple(
                        "Just-in-Time (JIT) Inventory",
                        "A management system that aims to minimize inventory levels by receiving materials only when needed for production.",
                        "Implementing a JIT inventory system to improve efficiency and reduce inventory costs."
                    ),
                    Triple(
                        "ABC Analysis",
                        "Classifying inventory items based on their value and criticality to optimize control and management efforts.",
                        "Applying ABC analysis to focus inventory management efforts on the most important items."
                    ),
                    Triple(
                        "Regression Analysis",
                        "Examining the relationship between two or more variables, often used to forecast future outcomes based on historical data.",
                        "Using regression analysis to predict sales trends or customer behavior."
                    ),
                    Triple(
                        "Correlation Analysis",
                        "Measuring the strength and direction of the linear relationship between two variables.",
                        "Calculating the correlation coefficient to assess the degree of association between variables."
                    ),
                    Triple(
                        "Decision Trees",
                        "Visual representations of possible choices and their potential consequences, helping to make informed decisions under uncertainty.",
                        "Creating a decision tree to analyze different investment options and evaluate their risks and rewards."
                    ),
                    Triple(
                        "Linear Programming",
                        "A mathematical technique for optimizing resource allocation within constraints, commonly used in production planning and scheduling.",
                        "Applying linear programming to determine the most efficient production plan that meets demand and resource constraints."
                    ),
                    Triple(
                        "Amortization",
                        "The process of allocating the cost of an asset over its useful life.",
                        "Amortizing the cost of a loan or intangible asset over time to reflect its declining value."
                    ),
                    Triple(
                        "Depreciation",
                        "The decline in the value of an asset over time due to wear and tear or obsolescence.",
                        "Depreciating the value of a physical asset, such as equipment, to reflect its usage and wear."
                    ),
                    Triple(
                        "Weighted Average Cost of Capital (WACC)",
                        "The average cost of a company's various financing sources, used to assess its overall capital cost and profitability.",
                        "A company calculates its WACC to determine the minimum rate of return it needs to generate from its investments to justify their cost."
                    ),
                    Triple(
                        "Capital Structure",
                        "The mix of debt and equity financing used by a company.",
                        "A company analyzes its capital structure to balance the potential benefits of leverage against the higher risk associated with debt."
                    ),
                    Triple(
                        "Financial Ratios",
                        "Quantitative measures used to assess a company's financial health and performance.",
                        "Investors use financial ratios like P/E ratio, debt-to-equity ratio, and return on equity to evaluate potential investments."
                    ),
                    Triple(
                        "Portfolio Theory",
                        "The study of constructing and managing investment portfolios to optimize risk and return.",
                        "Investors use Modern Portfolio Theory to diversify their portfolios and reduce risk without sacrificing expected returns."
                    ),
                    Triple(
                        "Options and Derivatives",
                        "Financial instruments that give the holder the right, but not the obligation, to buy or sell an underlying asset at a certain price by a certain date.",
                        "Companies and investors use options and derivatives to hedge risk, speculate on price movements, and generate income."
                    ),
                    Triple(
                        "Linear Programming",
                        "A mathematical technique used to optimize resource allocation within constraints.",
                        "A manufacturing company uses linear programming to determine the production schedule that minimizes costs while meeting demand."
                    ),
                    Triple(
                        "Inventory Management",
                        "The process of optimizing the levels and types of inventory a company holds to meet customer demand efficiently while minimizing holding costs.",
                        "A retailer uses forecasting techniques and inventory models to determine the optimal order quantity and reorder point for each product."
                    ),
                    Triple(
                        "Queuing Theory",
                        "The study of waiting lines and their impact on efficiency.",
                        "A bank uses queuing theory to analyze customer wait times and optimize the number of tellers needed to meet service level requirements."
                    ),
                    Triple(
                        "Decision Analysis",
                        "A framework for making informed decisions under uncertainty.",
                        "A company uses decision analysis to evaluate competing project proposals by considering potential risks, rewards, and expected outcomes."
                    ),
                    Triple(
                        "Simulation",
                        "A technique for modeling and analyzing complex systems by running them through a series of hypothetical scenarios.",
                        "An airline company uses simulation to test different pricing strategies and predict their impact on revenue and profitability."
                    ),
                    Triple(
                        "Financial Modeling",
                        "Building quantitative models to represent and analyze financial situations.",
                        "A company builds a financial model to forecast its future cash flow and evaluate different financing options."
                    ),
                    Triple(
                        "Monte Carlo Simulation",
                        "A statistical technique that uses random sampling to model and analyze risks and uncertainties.",
                        "A risk manager uses Monte Carlo simulation to estimate the potential losses from a natural disaster."
                    ),
                    Triple(
                        "Econometrics",
                        "The application of statistical methods to economic data in order to test economic theories and estimate relationships between economic variables.",
                        "An economist uses econometrics to analyze the impact of government policies on economic growth."
                    ),
                    Triple(
                        "Behavioral Finance",
                        "A field of finance that studies the influence of cognitive and emotional biases on financial decision-making.",
                        "Investors use behavioral finance insights to understand common investment mistakes and make more rational decisions."
                    ),
                    Triple(
                        "Financial Risk Management",
                        "The process of identifying, assessing, and mitigating financial risks.",
                        "A bank implements risk management practices to protect itself from potential losses due to credit defaults, market fluctuations, or operational failures."
                    ),
                    Triple(
                        "Corporate Finance",
                        "The area of finance that deals with the sources of funding, capital structure, and investment decisions of corporations.",
                        "A CFO uses corporate finance principles to make decisions about raising capital, investing in new projects, and managing dividends."
                    ),
                    Triple(
                        "International Finance",
                        "The study of financial transactions and institutions that operate across national borders.",
                        "A multinational corporation manages its foreign exchange exposure and navigates international tax laws."
                    ),
                    Triple(
                        "Financial Regulation",
                        "The set of laws, rules, and guidelines that govern the financial system.",
                        "Regulators implement financial regulations to protect investors, promote financial stability, and prevent financial crises."
                    ),
                    Triple(
                        "Market Efficiency",
                        "The degree to which asset prices reflect all available information.",
                        "Analyzing the efficiency of different markets to understand how quickly new information is incorporated into prices."
                    ),
                    Triple(
                        "Market Efficiency",
                        "The degree to which asset prices reflect all available information.",
                        "Analyzing the efficiency of different markets to understand how quickly new information is incorporated into prices."
                    ),
                    Triple(
                        "Market Microstructure",
                        "The study of the rules and institutions that govern how markets operate.",
                        "Understanding market microstructure can help investors identify trading opportunities and avoid trading costs."
                    ),
                    Triple(
                        "Trading Strategies",
                        "Rules or frameworks used to identify and capitalize on market opportunities.",
                        "Investors develop and implement trading strategies based on their risk tolerance, investment goals, and market analysis."
                    ),
                    Triple(
                        "Technical Analysis",
                        "The study of past price and volume data to predict future price movements.",
                        "Technical analysts use indicators and chart patterns to identify potential trading signals."
                    ),
                    Triple(
                        "Fundamental Analysis",
                        "The analysis of a company's financial statements and economic factors to assess its intrinsic value.",
                        "Fundamental analysts research companies to identify undervalued or overvalued investments."
                    ),
                    Triple(
                        "Swaps",
                        "Financial contracts that exchange cash flows based on different interest rates or indexes.",
                        "Companies use swaps to manage their interest rate risk and access lower borrowing costs."
                    ),
                    Triple(
                        "Futures",
                        "Standardized contracts to buy or sell an asset at a predetermined price on a future date.",
                        "Investors use futures to hedge their exposure to price movements in commodities or currencies."
                    ),
                    Triple(
                        "Options",
                        "Contracts that give the holder the right, but not the obligation, to buy or sell an asset at a certain price by a certain date.",
                        "Options provide investors with leverage and flexibility to manage risk and generate income."
                    ),
                    Triple(
                        "Stress Testing",
                        "Simulating how a portfolio or financial system would respond to extreme market conditions or unexpected events.",
                        "Stress testing helps identify potential vulnerabilities and improve risk management practices."
                    ),
                    Triple(
                        "Mergers and Acquisitions (M&A)",
                        "The consolidation of two or more companies through a combination of their assets and operations.",
                        "Companies engage in M&A to expand their market share, gain access to new technologies, or achieve operational synergies."
                    ),
                    Triple(
                        "Corporate Valuation",
                        "The process of estimating the economic value of a company.",
                        "Investors use valuation methods like P/E ratio, multiples analysis, and discounted cash flow to assess the worth of a company and make investment decisions."
                    ),
                    Triple(
                        "Real Options",
                        "The value of flexibility embedded in a company's assets or strategic options.",
                        "Identifying and valuing real options helps companies make informed decisions about future investments and growth opportunities."
                    ),
                    Triple(
                        "Dividend Policy",
                        "The decision of a company regarding how much of its profits to distribute to shareholders as dividends.",
                        "Companies consider factors like financial performance, growth prospects, and investor expectations when determining their dividend policy."
                    ),
                    Triple(
                        "Fintech",
                        "The use of technology to deliver financial services.",
                        "Fintech companies are disrupting traditional financial institutions by offering innovative and user-friendly services."
                    ),
                    Triple(
                        "Blockchain",
                        "A distributed ledger technology that securely records transactions in a transparent and tamper-proof way.",
                        "Blockchain has the potential to revolutionize various financial applications, including payments, asset management, and trade finance."
                    ),
                    Triple(
                        "Sustainable Finance",
                        "Investing in projects and companies that contribute to positive environmental and social outcomes.",
                        "Sustainable finance is gaining traction as investors increasingly seek to align their portfolios with environmental, social, and governance (ESG) principles."
                    ),
                    Triple(
                        "Accounting Equation",
                        "Assets = Liabilities + Equity, an underlying principle of double-entry accounting used to maintain financial balance.",
                        "Analyzing a company's balance sheet to understand its financial health and capital structure."
                    ),
                    Triple(
                        "Income Statement",
                        "Summarizes a company's revenues, expenses, and net income over a specific period.",
                        "Evaluating a company's profitability and operating efficiency by analyzing its income statement."
                    ),
                    Triple(
                        "Ratio Analysis",
                        "Using financial ratios like debt-to-equity ratio, current ratio, and return on assets to assess various aspects of a company's performance.",
                        "Comparing a company's financial ratios to industry benchmarks to identify strengths and weaknesses."
                    ),
                    Triple(
                        "Financial Forecasting",
                        "Predicting future financial performance based on historical data, market trends, and economic conditions.",
                        "Companies use financial forecasting to budget effectively and make informed investment decisions."
                    ),
                    Triple(
                        "Modern Portfolio Theory (MPT)",
                        "Optimizing portfolios for risk and return by diversifying across different asset classes.",
                        "Investors use MPT to construct portfolios that maximize expected returns at a given level of risk."
                    ),
                    Triple(
                        "Asset Allocation",
                        "Dividing investment capital among different asset classes like stocks, bonds, and real estate.",
                        "Determining an asset allocation strategy based on your individual risk tolerance and investment goals."
                    ),
                    Triple(
                        "Modern Portfolio Optimization (MPO)",
                        "Applying mathematical models to build efficient portfolios based on specific risk-return objectives.",
                        "Using MPO software to identify optimal asset allocations for various risk tolerance levels."
                    ),
                    Triple(
                        "Performance Attribution",
                        "Analyzing the sources of return for a portfolio to understand what contributed to its performance.",
                        "Identifying which asset classes or investment strategies have driven portfolio returns over time."
                    ),
                    Triple(
                        "Behavioral Finance",
                        "Studying how psychological and emotional factors influence investment decisions.",
                        "Understanding common investment biases and developing strategies to avoid them."
                    ),
                    Triple(
                        "Behavioral Economics",
                        "Applying psychology and cognitive science to understand economic decision-making.",
                        "Analyzing consumer behavior and market responses to understand consumer preferences and market trends."
                    ),
                    Triple(
                        "Quantitative Finance",
                        "Applying mathematical and statistical tools to analyze financial markets and develop trading strategies.",
                        "Building quantitative models to predict market movements and manage risk in complex financial instruments."
                    ),
                    Triple(
                        "Computational Finance",
                        "Leveraging computational methods and algorithms to solve financial problems and analyze complex data sets.",
                        "Developing simulation models and machine learning algorithms to support investment decisions and risk management."
                    ),
                    Triple(
                        "Financial Big Data",
                        "Analyzing large datasets of financial information to extract insights and make informed decisions.",
                        "Using big data analytics to identify market trends, detect fraud, and personalize financial services."
                    ),
                    Triple(
                        "Financial Technology (FinTech)",
                        "Leveraging technology to develop innovative financial products and services.",
                        "Utilizing blockchain technology for secure transactions and decentralized financial services."
                    ),
                    Triple(
                        "Capital Budgeting Techniques",
                        "Methods for selecting long-term investment projects, including Net Present Value (NPV), Internal Rate of Return (IRR), and Payback Period.",
                        "A company uses NPV to compare different expansion projects and prioritize investments."
                    ),
                    Triple(
                        "Mergers and Acquisitions (M&A)",
                        "Consolidation of two or more companies through various approaches like mergers, acquisitions, or takeovers.",
                        "A large corporation acquires a smaller competitor to expand its market share and product portfolio."
                    ),
                    Triple(
                        "Leveraged Buyout (LBO)",
                        "Acquisition of a company financed primarily through debt, often used by private equity firms.",
                        "An LBO can unlock value in a company by restructuring its capital structure and improving operating efficiency."
                    ),
                    Triple(
                        "Corporate Restructuring",
                        "Reorganizing a company's structure, operations, or assets to improve profitability and competitiveness.",
                        "A company undergoing financial distress may implement a restructuring plan to reduce costs and generate cash."
                    ),
                    Triple(
                        "Value at Risk (VaR)",
                        "A statistical measure of the potential loss of a portfolio within a given time frame and confidence level.",
                        "Financial institutions use VaR to manage their risk exposure and capital allocation."
                    ),
                    Triple(
                        "Stress Testing",
                        "Simulating how a portfolio or financial system would respond to extreme market conditions or unexpected events.",
                        "Banks conduct stress tests to ensure they have sufficient capital to withstand potential financial crises."
                    ),
                    Triple(
                        "Sensitivity Analysis",
                        "Examining how changes in assumptions or variables affect the outcome of a financial model.",
                        "Investors conduct sensitivity analysis to understand how potential economic changes could impact their portfolio value."
                    ),
                    Triple(
                        "Monte Carlo Simulation",
                        "A statistical technique using random sampling to model and analyze risks and uncertainties in financial scenarios.",
                        "Insurance companies use Monte Carlo simulations to estimate potential losses from natural disasters."
                    ),
                    Triple(
                        "Financial Modeling",
                        "Building quantitative models to represent and analyze financial situations, such as forecasting future cash flows or valuing companies.",
                        "A startup uses a financial model to project its future revenue and expenses to secure funding from investors."
                    ),
                    Triple(
                        "Foreign Exchange Markets",
                        "Where currencies are traded and exchange rates are determined.",
                        "Companies operating in multiple countries need to manage their foreign exchange exposure to protect against currency fluctuations."
                    ),
                    Triple(
                        "International Trade Finance",
                        "Providing financial instruments and services to facilitate international trade and investment.",
                        "Letters of credit and export credit insurance help mitigate risks for exporters and importers."
                    ),
                    Triple(
                        "Emerging Markets",
                        "Economies experiencing rapid growth and development, often with higher returns but also higher risks for investors.",
                        "Investors considering investing in emerging markets need to understand the unique risks and opportunities associated with these economies."
                    ),
                    Triple(
                        "Global Financial Crisis",
                        "A major financial crisis affecting interconnected financial markets worldwide.",
                        "The 2008 financial crisis is a cautionary tale for understanding the systemic risks inherent in global financial systems."
                    ),
                    Triple(
                        "Sustainable Finance",
                        "Investing in projects and companies that contribute to positive environmental and social outcomes.",
                        "Investors increasingly seek ESG-focused investments to align their portfolios with ethical and sustainable practices."
                    ),
                    Triple(
                        "Callable Bond",
                        "A bond that can be redeemed by the issuer before its maturity date.",
                        "Companies may issue callable bonds to take advantage of lower interest rates in the future."
                    ),
                    Triple(
                        "Dupont Analysis",
                        "A method of breaking down ROE into its component parts to assess a company's efficiency, profitability, and financial leverage.",
                        "Dupont analysis helps identify areas for improvement in a company's return on equity."
                    ),
                    Triple(
                        "Capital Budgeting",
                        "The process of evaluating and selecting long-term investments that align with a company's strategic goals.",
                        "Capital budgeting involves analyzing projects such as new facilities or equipment acquisitions."
                    ),
                    Triple(
                        "Callable Preferred Stock",
                        "Preferred stock that the issuer can redeem at a specified price after a certain date.",
                        "Callable preferred stock gives the issuer flexibility in managing its capital structure."
                    ),
                    Triple(
                        "Working Capital Turnover Ratio",
                        "Measures how efficiently a company utilizes its working capital to generate sales.",
                        "A high working capital turnover ratio suggests effective management of short-term assets and liabilities."
                    ),
                    Triple(
                        "Cost of Goods Manufactured (COGM)",
                        "The total production cost of goods completed during a specific period.",
                        "COGM includes direct materials, direct labor, and manufacturing overhead."
                    ),
                    Triple(
                        "Payback Period",
                        "The time it takes for an investment to generate cash inflows sufficient to recover its initial cost.",
                        "A shorter payback period is often preferred, indicating a quicker return on investment."
                    ),
                    Triple(
                        "Predatory Pricing",
                        "A strategy where a company sets low prices to eliminate competitors and gain market share.",
                        "Some argue that predatory pricing can harm competition in the long run."
                    ),
                    Triple(
                        "Hedging",
                        "A risk management strategy that involves using financial instruments to offset potential losses in investments.",
                        "Businesses may use derivatives like futures contracts to hedge against fluctuating commodity prices."
                    ),
                    Triple(
                        "Straddle",
                        "An options trading strategy involving the purchase of both a call and a put option with the same strike price and expiration date.",
                        "Implementing a straddle to profit from significant price volatility in the underlying asset."
                    ),
                    Triple(
                        "Multivariate Analysis",
                        "Statistical analysis that involves the simultaneous examination of two or more variables to understand their relationships.",
                        "Studying the impact of both price and advertising expenditure on the sales of a product."
                    ),
                    Triple(
                        "Repo Rate (Repurchase Agreement Rate)",
                        "The interest rate at which a financial institution sells securities to another institution with an agreement to repurchase them at a higher price in the future.",
                        "Banks using repo transactions to manage their short-term liquidity needs."
                    ),
                    Triple(
                        "Bootstrapped Confidence Intervals",
                        "Confidence intervals derived through bootstrapping, a resampling technique, to estimate the uncertainty around a statistical measure.",
                        "Calculating a bootstrapped confidence interval for the mean return of a portfolio."
                    ),
                    Triple(
                        "Cramér's Rule",
                        "A mathematical technique used to solve a system of linear equations by expressing each variable's solution as a determinant ratio.",
                        "Applying Cramér's Rule to solve a system of equations in economic modeling."
                    ),
                    Triple(
                        "Geometric Brownian Motion",
                        "A mathematical model describing the random motion of particles, often used to model asset prices in finance.",
                        "Simulating stock price movements using geometric Brownian motion in option pricing."
                    ),
                    Triple(
                        "Discount Rate",
                        "The rate used to discount future cash flows to their present value, reflecting the time value of money.",
                        "Discounting future earnings to determine the present value of a business."
                    ),
                    Triple(
                        "Pareto Analysis",
                        "Identifying the 20% of factors that create 80% of the effects, focusing efforts on maximizing impact.",
                        "Marketing teams use Pareto analysis to prioritize their campaign activities for better ROI."
                    ),
                    Triple(
                        "Impulse Buying",
                        "The unplanned purchase of goods or services due to immediate desire or external triggers.",
                        "Retailers can encourage impulse buying by strategically placing high-margin items near checkout counters."
                    ),
                    Triple(
                        "Risk Pooling",
                        "Combining resources or exposures to lower individual risk and spread potential losses across a larger group.",
                        "Insurance companies pool premiums from many individuals to manage the financial impact of unforeseen events."
                    ),
                    Triple(
                        "Blue Ocean Strategy",
                        "Creating new market space with uncontested competition instead of fighting for existing share.",
                        "Airbnb disrupted the hospitality industry by offering a novel accommodation experience outside traditional hotels."
                    ),
                    Triple(
                        "Principal-Agent Problem",
                        "A conflict of interest between a principal (decision-maker) and their agent (acting party) due to information asymmetry.",
                        "Companies mitigate this by aligning incentives, improving communication, and implementing monitoring systems."
                    ),
                    Triple(
                        "Econometrics",
                        "Applying statistical methods to economic data to test economic theories and forecast future trends.",
                        "Central banks use econometric models to analyze inflation and set interest rates."
                    ),
                    Triple(
                        "Game Theory",
                        "Studying strategic interactions between rational decision-makers to predict their behavior and outcomes in competitive scenarios.",
                        "Game theory helps understand pricing strategies in oligopoly markets or negotiation tactics in business deals."
                    ),
                    Triple(
                        "Strategic Foresight",
                        "The ability to anticipate future trends and developments to make informed decisions in a changing environment.",
                        "Companies conduct scenario planning exercises to prepare for potential disruptions and identify strategic opportunities."
                    ),
                    Triple(
                        "Data Mining",
                        "Extracting meaningful patterns and insights from large datasets to inform decision-making and problem-solving.",
                        "Retailers use data mining to personalize customer recommendations and optimize product placement."
                    ),
                    Triple(
                        "Grey Market",
                        "Trade of goods through unauthorized or unofficial channels, often operating outside traditional distribution networks.",
                        "Luxury brands may face challenges with grey markets selling counterfeit or parallel imports of their products."
                    ),
                    Triple(
                        "Profit",
                        "Financial gain where revenue exceeds expenses.",
                        "A company earned $50,000 in sales and incurred $40,000 in costs, resulting in a profit of $10,000."
                    ),
                    Triple(
                        "Marginal Cost",
                        "The additional cost of producing one more unit of a product or service.",
                        "Calculating the marginal cost to determine the optimal production level."
                    ),
                    Triple(
                        "Financial Statement",
                        "A formal record of the financial activities and position of a business, person, or other entity.",
                        "Analyzing financial statements to assess a company's performance and financial health."
                    ),
                    Triple(
                        "Financial Analysis",
                        "The process of evaluating financial data to make informed decisions about a company's operations and investments.",
                        "Using financial ratios to assess liquidity, profitability, and efficiency."
                    ),
                    Triple(
                        "Cash Flow Statement",
                        "A financial statement that shows the inflows and outflows of cash and cash equivalents over a specific period.",
                        "Analyzing cash flow from operating, investing, and financing activities to understand a company's cash position."
                    ),
                    Triple(
                        "Financial Planning",
                        "The process of developing strategies to achieve financial goals and objectives.",
                        "Creating a budget and forecasting future cash flows to guide decision-making."
                    ),
                    Triple(
                        "Cost of Capital",
                        "The cost of obtaining funds to finance a business, including both debt and equity.",
                        "Determining the weighted average cost of capital (WACC) to evaluate investment opportunities."
                    ),
                    Triple(
                        "Financial Risk",
                        "The possibility of losing money or experiencing negative financial consequences.",
                        "Assessing market risk, credit risk, and operational risk to make informed investment decisions."
                    ),
                    Triple(
                        "Capital Budgeting",
                        "The process of evaluating and selecting long-term investment projects.",
                        "Using techniques such as net present value (NPV) and internal rate of return (IRR) to prioritize projects."
                    ),
                    Triple(
                        "Financial Forecasting",
                        "The process of estimating future financial outcomes based on historical data and trends.",
                        "Creating financial projections to guide strategic decision-making and resource allocation."
                    ),
                    Triple(
                        "Cost Management",
                        "The process of controlling and reducing expenses to improve profitability.",
                        "Implementing cost-saving measures and monitoring expenses to achieve financial objectives."
                    ),

                    Triple(
                        "Working Capital",
                        "The difference between current assets and current liabilities, representing the funds available for day-to-day operations.",
                        "Managing working capital to ensure sufficient liquidity for operational needs."
                    ),
                    Triple(
                        "Financial Forecasting",
                        "The process of estimating future financial outcomes based on historical data and trends.",
                        "Using techniques such as time series analysis and regression analysis to make accurate financial predictions."
                    ),
                    Triple(
                        "Cost of Goods Sold (COGS)",
                        "The direct costs attributable to the production of goods sold by a company.",
                        "Calculating COGS to determine gross profit margin and assess cost efficiency."
                    ),
                    Triple(
                        "Financial Model",
                        "A mathematical representation of a company's financial performance and position.",
                        "Building a financial model to simulate different scenarios and evaluate the impact on profitability and cash flow."
                    ),
                    Triple(
                        "Financial Statement Analysis",
                        "The process of examining a company's financial statements to assess its financial health and performance.",
                        "Performing ratio analysis and trend analysis to identify strengths and weaknesses in a company's financial position."
                    ),
                    Triple(
                        "Tax Planning",
                        "Strategies to minimize tax liabilities and optimize financial outcomes.",
                        "Utilizing tax credits, deductions, and deferral strategies to maximize after-tax income."
                    ),
                    Triple(
                        "Financial Reporting Standards",
                        "Guidelines and rules for preparing and presenting financial statements.",
                        "Complying with Generally Accepted Accounting Principles (GAAP) or International Financial Reporting Standards (IFRS)."
                    ),
                    Triple(
                        "Cash Management",
                        "The process of managing cash flows, liquidity, and investments.",
                        "Implementing cash pooling techniques and optimizing cash conversion cycles to improve cash efficiency."
                    ),
                    Triple(
                        "Cost Behavior",
                        "The way in which costs change in relation to changes in activity levels.",
                        "Understanding fixed costs, variable costs, and semi-variable costs to make informed pricing and production decisions."
                    ),
                    Triple(
                        "Financial Distress",
                        "A condition in which a company is unable to meet its financial obligations.",
                        "Identifying early warning signs of financial distress and implementing turnaround strategies to improve financial stability."
                    ),
                    Triple(
                        "Credit Management",
                        "The process of granting credit, setting credit terms, and collecting payments from customers.",
                        "Establishing credit policies, assessing creditworthiness, and monitoring accounts receivable to minimize bad debt losses."
                    ),
                    Triple(
                        "Financial Performance",
                        "An assessment of how well a company is generating revenue and profits relative to its assets, liabilities, and equity.",
                        "Analyzing key performance indicators (KPIs) such as return on investment (ROI) and profit margin to evaluate financial health."
                    ),
                    Triple(
                        "Financial Controls",
                        "Policies, procedures, and processes designed to safeguard a company's assets and ensure the accuracy of its financial information.",
                        "Implementing internal controls such as segregation of duties and authorization procedures to prevent fraud and errors."
                    ),
                    Triple(
                        "Strategic Financial Management",
                        "The process of aligning financial goals and strategies with overall business objectives.",
                        "Developing financial strategies to support growth, expansion, and long-term sustainability."
                    ),
                    Triple(
                        "Corporate Finance",
                        "The area of finance that deals with sources of funding and the capital structure of corporations.",
                        "Raising capital through equity or debt financing, and making investment decisions to maximize shareholder value."
                    ),
                    Triple(
                        "Introduction to Accounting",
                        "Introduction to the fundamental concepts and principles of accounting, including the recording, summarizing, and analyzing of financial transactions.",
                        "Studying the basics of accounting to understand how businesses track their financial activities."
                    ),
                    Triple(
                        "Why Accounting is Important",
                        "Accounting is essential for providing accurate and reliable financial information to stakeholders, facilitating decision-making, and ensuring transparency and accountability.",
                        "Using accounting information to assess the financial health and performance of a business before making investment decisions."
                    ),
                    Triple(
                        "Nature of Accounting",
                        "Accounting is both an art and a science that involves systematic recording, classification, summarization, analysis, and interpretation of financial data.",
                        "Applying accounting principles and standards to prepare financial statements that accurately reflect the financial position of a company."
                    ),
                    Triple(
                        "Branches of Accounting",
                        "Different specialized areas within the field of accounting that focus on specific aspects of financial information, such as financial accounting, managerial accounting, tax accounting, auditing, and forensic accounting.",
                        "Hiring a forensic accountant to investigate financial fraud or misconduct within a company."
                    ),
                    Triple(
                        "Accounting Information",
                        "Financial data that is recorded, summarized, and communicated to stakeholders to facilitate decision-making.",
                        "Analyzing accounting information to determine the profitability and liquidity of a business."
                    ),
                    Triple(
                        "Internal Users of Accounting",
                        "Individuals or groups within a business organization who use accounting information for decision-making purposes, such as management, employees, and internal auditors.",
                        "Management using financial reports to assess the performance of different departments and make strategic decisions."
                    ),
                    Triple(
                        "External Users of Accounting",
                        "Individuals or entities outside of a business organization who rely on accounting information for decision-making purposes, such as investors, creditors, government agencies, regulatory bodies, and analysts.",
                        "Investors analyzing financial statements to assess the financial health and stability of a company before investing."
                    ),
                    Triple(
                        "Business Organization",
                        "Different legal structures in which businesses are formed and operated, including sole proprietorship, partnership, corporation, and cooperative.",
                        "Registering a new business as a corporation to limit personal liability for business debts."
                    ),
                    Triple(
                        "Sole Proprietorship",
                        "A business owned and operated by a single individual, who retains all profits and is personally liable for all debts and obligations.",
                        "Starting a small consulting business as a sole proprietorship."
                    ),
                    Triple(
                        "Partnership",
                        "A business structure in which two or more individuals share ownership, profits, and liabilities.",
                        "Forming a partnership with a colleague to launch a new restaurant."
                    ),
                    Triple(
                        "Corporation",
                        "A legal entity that is separate from its owners, with limited liability for shareholders and the ability to raise capital through the sale of stock.",
                        "Investing in shares of a publicly traded corporation on the stock market."
                    ),
                    Triple(
                        "Cooperative",
                        "An organization owned and operated by its members for their mutual benefit, typically in agriculture, consumer goods, or services.",
                        "Joining a cooperative to purchase goods or services at lower prices."
                    ),
                    Triple(
                        "Service Companies",
                        "Businesses that provide intangible products or services to customers, such as consulting, legal, and accounting firms.",
                        "Hiring a consulting firm to provide strategic advice for business expansion."
                    ),
                    Triple(
                        "Merchandising Companies",
                        "Businesses that buy finished products and resell them to customers without altering their form, such as retail stores.",
                        "Shopping at a clothing store that buys clothes from manufacturers and sells them to consumers."
                    ),
                    Triple(
                        "Manufacturing Companies",
                        "Businesses that produce tangible goods by converting raw materials or components into finished products, such as automotive or electronics manufacturers.",
                        "Buying a smartphone produced by a leading electronics manufacturing company."
                    ),
                    Triple(
                        "Accrual Accounting",
                        "An accounting method that records revenues and expenses when they are earned or incurred, regardless of when cash is received or paid.",
                        "Recognizing revenue from a sale at the time of delivery, even if the customer has not yet paid."
                    ),
                    Triple(
                        "Judgement and Estimates",
                        "The use of professional judgement and reasonable estimates in preparing financial statements, particularly for uncertain or complex transactions.",
                        "Estimating the useful life of an asset for depreciation purposes based on historical data and industry standards."
                    ),
                    Triple(
                        "Prudence",
                        "A principle in accounting that advocates for caution in recognizing revenues and gains, but recognizing expenses and losses as soon as they are reasonably possible.",
                        "Adjusting inventory values downward to reflect declines in market value or obsolescence."
                    ),
                    Triple(
                        "Substance Over Form",
                        "An accounting principle that emphasizes the economic substance of transactions over their legal form.",
                        "Treating a lease that effectively transfers ownership rights as a capital lease, even if it is structured as an operating lease."
                    ),
                    Triple(
                        "Going Concern Assumption",
                        "An assumption in accounting that a business will continue to operate indefinitely, allowing it to use the accrual basis of accounting and report assets at their historical cost.",
                        "Preparing financial statements under the assumption that the company will remain in operation for the foreseeable future."
                    ),
                    Triple(
                        "Time Period Assumption",
                        "An assumption in accounting that divides the economic life of a business into specific time periods for reporting purposes, such as monthly, quarterly, or annually.",
                        "Preparing monthly financial statements to track performance and monitor financial health."
                    ),
                    Triple(
                        "Generally Accepted Accounting Principles (GAAP)",
                        "A set of standard accounting principles, standards, and procedures that govern financial reporting in the United States, ensuring consistency, comparability, and reliability of financial statements across different entities.",
                        "Preparing financial statements in accordance with GAAP to comply with regulatory requirements and provide reliable information to stakeholders."
                    ),
                    Triple(
                        "International Financial Reporting Standards (IFRS)",
                        "A set of global accounting standards developed by the International Accounting Standards Board (IASB) for the preparation and presentation of financial statements, harmonizing accounting practices and facilitating cross-border comparisons of financial information.",
                        "Adopting IFRS to improve transparency, comparability, and credibility of financial reporting for multinational companies."
                    ),
                    Triple(
                        "Financial and Sustainability Reporting Standards Council (FSRSC)",
                        "An organization that develops and promotes financial and sustainability reporting standards to enhance transparency and accountability in corporate reporting, issuing guidelines and frameworks for integrating financial and non-financial information in corporate reports.",
                        "Implementing FSRSC reporting standards to provide stakeholders with a comprehensive view of a company's financial and sustainability performance."
                    ),
                    Triple(
                        "Statement of Assets, Liabilities, and Net Worth (SALN)",
                        "A financial statement that shows the assets owned, liabilities owed, and net worth of an individual or organization at a specific point in time, preparing SALN to assess financial health, track changes in wealth, and evaluate solvency.",
                        "Reviewing the SALN of a business to evaluate its financial position and determine its ability to meet its financial obligations."
                    ),
                    Triple(
                        "Accounting Equation",
                        "A fundamental principle in accounting that states that assets equal liabilities plus equity, balancing the accounting equation to ensure that resources owned by a business are equal to the claims against those resources.",
                        "Verifying the accuracy of the accounting equation by comparing assets to liabilities plus equity."
                    ),
                    Triple(
                        "Financial Statements",
                        "Documents that summarize a company's financial transactions, providing information about its financial position, performance, and cash flows.",
                        "Preparing financial statements such as the balance sheet, income statement, and cash flow statement for external reporting purposes."
                    ),
                    Triple(
                        "Balance Sheet",
                        "A financial statement that reports a company's assets, liabilities, and shareholders' equity at a specific point in time, providing a snapshot of its financial position.",
                        "Analyzing the balance sheet to assess a company's liquidity, solvency, and leverage."
                    ),
                    Triple(
                        "Income Statement",
                        "A financial statement that reports a company's revenues, expenses, and net income over a specific period, indicating its profitability.",
                        "Reviewing the income statement to evaluate a company's revenue-generating and cost-controlling activities."
                    ),
                    Triple(
                        "Cash Flow Statement",
                        "A financial statement that reports a company's cash inflows and outflows from operating, investing, and financing activities over a specific period, providing insights into its liquidity and cash management.",
                        "Analyzing the cash flow statement to assess a company's ability to generate cash and meet its financial obligations."
                    ),
                    Triple(
                        "Financial Ratios",
                        "Quantitative metrics calculated from financial statements to evaluate various aspects of a company's performance, profitability, liquidity, and solvency.",
                        "Calculating financial ratios such as return on assets (ROA), debt-to-equity ratio, and current ratio to assess a company's financial health."
                    ),
                    Triple(
                        "Internal Controls",
                        "Policies, procedures, and processes implemented by a company to safeguard its assets, ensure the accuracy of its financial records, and prevent fraud and errors.",
                        "Establishing segregation of duties, authorization procedures, and physical controls to protect company assets and data."
                    ),
                    Triple(
                        "Auditing",
                        "An independent examination of a company's financial statements and internal controls by a qualified auditor to provide assurance on their accuracy, completeness, and compliance with applicable laws and regulations.",
                        "Hiring an external auditor to conduct a financial statement audit and issue an audit report for stakeholders."
                    ),
                    Triple(
                        "Taxation",
                        "The process of imposing compulsory levies on individuals or entities by governments to fund public expenditures and services.",
                        "Filing annual tax returns and complying with tax laws and regulations to fulfill tax obligations."
                    ),
                    Triple(
                        "Cost Accounting",
                        "A branch of accounting that focuses on recording, analyzing, and allocating costs associated with producing goods or services.",
                        "Using cost accounting techniques such as job costing and activity-based costing to determine product costs and make pricing decisions."
                    ),
                    Triple(
                        "Managerial Accounting",
                        "A branch of accounting that provides financial information and analysis to internal users, such as management, to support decision-making, planning, and control.",
                        "Preparing budgets, variance analysis reports, and performance metrics for managerial decision-making."
                    ),
                    Triple(
                        "Forensic Accounting",
                        "A specialized field of accounting that investigates financial transactions and activities to uncover fraud, embezzlement, or other illegal conduct.",
                        "Conducting forensic accounting investigations to gather evidence for legal proceedings or regulatory enforcement actions."
                    ),
                    Triple(
                        "Financial Planning and Analysis (FP&A)",
                        "A strategic function within a company that involves financial forecasting, budgeting, and analysis to support decision-making, planning, and performance management.",
                        "Collaborating with business units to develop financial forecasts and budgets, and analyzing variances to drive business performance."
                    ),
                    Triple(
                        "Cost-Volume-Profit (CVP) Analysis",
                        "A managerial accounting technique used to analyze the relationship between costs, volume, and profits to assess the impact of changes in production levels, sales prices, and costs on profitability.",
                        "Conducting CVP analysis to determine the breakeven point and evaluate the profitability of new product lines."
                    ),
                    Triple(
                        "Financial Management",
                        "The process of planning, organizing, directing, and controlling a company's financial resources to achieve its objectives and maximize shareholder wealth.",
                        "Implementing financial strategies such as capital budgeting, financing decisions, and risk management to optimize financial performance."
                    ),
                    Triple(
                        "Corporate Finance",
                        "The area of finance that deals with sources of funding and the capital structure of corporations, including investment decisions, financing decisions, and dividend policy.",
                        "Raising capital through debt or equity financing and evaluating investment opportunities to maximize shareholder value."
                    ),
                    Triple(
                        "Cost Benefit Analysis",
                        "A technique used to evaluate the costs and benefits of a decision, project, or investment to determine its economic feasibility and potential return.",
                        "Conducting a cost benefit analysis to assess whether implementing a new technology system will generate sufficient cost savings and benefits."
                    ),
                    Triple(
                        "Financial Risk Management",
                        "The process of identifying, assessing, and mitigating risks related to financial markets, assets, liabilities, and operations to protect a company's financial health and minimize losses.",
                        "Implementing hedging strategies, diversifying investments, and setting risk tolerance levels to manage financial risks effectively."
                    ),
                    Triple(
                        "Ethical Considerations in Accounting",
                        "The principles, standards, and guidelines that govern ethical behavior and decision-making in the accounting profession, including integrity, objectivity, confidentiality, and professional competence.",
                        "Adhering to ethical standards and codes of conduct when preparing financial statements, conducting audits, and providing financial advice."
                    ),
                    Triple(
                        "Depreciation",
                        "The allocation of the cost of a tangible asset over its useful life.",
                        "Recording depreciation expense to reflect the consumption of an asset's economic benefits over time."
                    ),
                    Triple(
                        "Inventory",
                        "The goods and materials held by a business for the purpose of resale or production.",
                        "Valuing inventory at the lower of cost or market value to ensure accurate financial reporting."
                    ),
                    Triple(
                        "Equity",
                        "The ownership interest in a company represented by its shares.",
                        "Issuing additional shares of stock to raise capital for business expansion."
                    ),
                    Triple(
                        "Revenue",
                        "The income generated by a business from its primary activities, such as sales of goods or services.",
                        "Recognizing revenue from product sales when goods are delivered to customers."
                    ),
                    Triple(
                        "Expense",
                        "The costs incurred by a business in its normal operations to generate revenue.",
                        "Recording salary expense for employees' wages and benefits."
                    ),
                    Triple(
                        "Liability",
                        "An obligation or debt owed by a business to external parties.",
                        "Accruing for unpaid expenses such as utilities or rent."
                    ),
                    Triple(
                        "Asset",
                        "A resource owned or controlled by a business that is expected to provide future economic benefits.",
                        "Recording the purchase of equipment as a non-current asset on the balance sheet."
                    ),
                    Triple(
                        "Capital",
                        "Financial assets or resources used by a business to generate income.",
                        "Investing additional capital in a business to fund expansion projects."
                    ),
                    Triple(
                        "Interest",
                        "The cost of borrowing money or the return earned on invested capital.",
                        "Accruing interest expense on outstanding loans payable."
                    ),
                    Triple(
                        "Dividend",
                        "A distribution of profits by a corporation to its shareholders.",
                        "Declaring a cash dividend to be paid to shareholders from retained earnings."
                    ),
                    Triple(
                        "Amortization",
                        "The process of spreading the cost of intangible assets over their useful life.",
                        "Amortizing the cost of a patent over its legal life."
                    ),
                    Triple(
                        "Gross Profit",
                        "The difference between revenue and the cost of goods sold.",
                        "Calculating gross profit by subtracting cost of goods sold from total revenue."
                    ),
                    Triple(
                        "Net Income",
                        "The profit earned by a business after deducting all expenses from total revenue.",
                        "Determining net income by subtracting total expenses from total revenue."
                    ),
                    Triple(
                        "Operating Income",
                        "The profit generated from a company's core business operations, excluding non-operating income and expenses.",
                        "Calculating operating income by subtracting operating expenses from gross profit."
                    ),
                    Triple(
                        "Retained Earnings",
                        "The accumulated profits retained by a company after paying dividends to shareholders.",
                        "Increasing retained earnings by reinvesting profits back into the business."
                    ),
                    Triple(
                        "Cash",
                        "The most liquid asset of a business, consisting of physical currency and balances in bank accounts.",
                        "Maintaining sufficient cash reserves to meet short-term obligations."
                    ),
                    Triple(
                        "Debt",
                        "Money borrowed by a business that must be repaid, usually with interest.",
                        "Issuing bonds to raise capital for long-term investments."
                    ),
                    Triple(
                        "Credit",
                        "An arrangement in which a business receives goods or services before paying for them, based on trust or a promise to pay later.",
                        "Purchasing inventory on credit from suppliers."
                    ),
                    Triple(
                        "Taxes",
                        "Compulsory levies imposed by governments on individuals and businesses to fund public expenditures and services.",
                        "Paying income taxes to the government based on taxable income."
                    ),
                    Triple(
                        "Equation",
                        "A mathematical statement that shows the equality between two expressions.",
                        "Balancing the accounting equation by ensuring that assets equal liabilities plus equity."
                    ),
                    Triple(
                        "Auditor",
                        "Individual or firm responsible for examining and verifying a company's financial records and reports.",
                        "The auditor reviewed the company's financial statements to ensure compliance with accounting standards."
                    ),
                    Triple(
                        "Depreciation",
                        "Allocation of the cost of a tangible asset over its useful life, reflecting its gradual wear and tear and decline in value.",
                        "The company recorded depreciation expense on its equipment to reflect its usage over time."
                    ),
                    Triple(
                        "Equity",
                        "Ownership interest in a company, representing the residual value of assets after deducting liabilities.",
                        "Shareholders' equity increased as a result of the company's retained earnings and additional capital contributions."
                    ),
                    Triple(
                        "Dividend",
                        "Distribution of a portion of a company's earnings to its shareholders, typically in cash or additional shares.",
                        "The board of directors declared a dividend payment to shareholders for the fiscal year."
                    ),
                    Triple(
                        "Asset",
                        "Resource with economic value owned or controlled by a company, expected to provide future benefits.",
                        "The company invested in new equipment as an asset to support its production operations."
                    ),
                    Triple(
                        "Liability",
                        "Obligation or debt owed by a company to external parties, arising from past transactions or events.",
                        "The company recorded a liability for its outstanding loans from financial institutions."
                    ),
                    Triple(
                        "Revenue",
                        "Income generated from the sale of goods or services, representing the company's primary source of earnings.",
                        "The company recognized revenue from the sale of its products to customers."
                    ),
                    Triple(
                        "Expense",
                        "Cost incurred in the process of generating revenue or operating a business, representing the outflow of resources.",
                        "The company incurred expenses for salaries, rent, and utilities to support its operations."
                    ),
                    Triple(
                        "Gross Profit",
                        "Profit earned by a company after deducting the cost of goods sold (COGS) from its total revenue.",
                        "The company's gross profit margin increased due to higher sales revenue and lower COGS."
                    ),
                    Triple(
                        "Net Income",
                        "Profit earned by a company after deducting all expenses, taxes, and other deductions from its total revenue.",
                        "The company reported a net income of $1 million for the fiscal year."
                    ),
                    Triple(
                        "Cash Flow",
                        "Movement of cash into or out of a business, reflecting its liquidity and ability to meet short-term obligations.",
                        "The company's positive cash flow allowed it to invest in new projects and repay its debts."
                    ),
                    Triple(
                        "Debt",
                        "Amount of money borrowed by a company from external parties, typically with a promise of repayment with interest.",
                        "The company issued bonds to raise capital and incurred long-term debt obligations."
                    ),
                    Triple(
                        "Financial Analysis",
                        "Assessment of a company's financial performance and position through the interpretation of financial statements and other relevant data.",
                        "The financial analyst conducted a comprehensive financial analysis to evaluate the company's profitability and liquidity."
                    ),
                    Triple(
                        "Taxation",
                        "Imposition of compulsory levies by governments on individuals and entities to fund public expenditures and services.",
                        "The company consulted with tax experts to minimize its tax liabilities through strategic tax planning."
                    ),
                    Triple(
                        "Budget",
                        "Financial plan that outlines projected revenues and expenses for a specific period, serving as a tool for controlling costs and allocating resources.",
                        "The company prepared an annual budget to allocate funds for various departments and activities."
                    ),
                    Triple(
                        "Variance",
                        "Difference between planned or budgeted amounts and actual results, indicating deviations from expected performance.",
                        "The finance team analyzed the variance between budgeted and actual expenses to identify areas for cost reduction."
                    ),
                    Triple(
                        "Risk Management",
                        "Process of identifying, assessing, and mitigating potential threats or uncertainties that may impact a company's objectives.",
                        "The company implemented risk management strategies to protect against financial, operational, and strategic risks."
                    ),
                    Triple(
                        "Profitability",
                        "Ability of a company to generate profit relative to its revenue, assets, or equity, indicating its efficiency and financial performance.",
                        "The company's profitability increased due to higher sales revenue and improved cost management."
                    ),
                    Triple(
                        "Liquidity",
                        "Ability of a company to meet its short-term obligations and convert assets into cash without significant loss.",
                        "The company maintained sufficient liquidity to cover its operating expenses and short-term debt obligations."
                    ),
                    Triple(
                        "Solvency",
                        "Ability of a company to meet its long-term financial obligations and continue operating indefinitely, indicating its financial stability.",
                        "The company's strong balance sheet and positive cash flow demonstrated its solvency and ability to sustain operations."
                    ),
                    Triple(
                        "Introduction to Economics",
                        "The study of how societies allocate scarce resources to satisfy unlimited wants and needs.",
                        "Understanding the basic principles of economics is essential for analyzing decision-making and resource allocation in society."
                    ),
                    Triple(
                        "Economic Development",
                        "The process by which a nation's economy grows and improves the standard of living for its citizens.",
                        "Government policies aimed at promoting education, infrastructure development, and investment in human capital contribute to economic development."
                    ),
                    Triple(
                        "Economic History",
                        "The study of past economic events, trends, and policies to understand their impact on the present and future.",
                        "Analyzing the Great Depression of the 1930s provides insights into the causes and consequences of economic downturns."
                    ),
                    Triple(
                        "GDP & GNP",
                        "Gross Domestic Product (GDP) measures the total value of goods and services produced within a country's borders, while Gross National Product (GNP) measures the total value of goods and services produced by a country's residents, regardless of location.",
                        "The government uses GDP and GNP data to assess the economic performance and growth of a nation."
                    ),
                    Triple(
                        "Basic Principle of Demand and Supply",
                        "The law of demand states that as the price of a good or service increases, the quantity demanded decreases, while the law of supply states that as the price of a good or service increases, the quantity supplied increases.",
                        "The equilibrium price and quantity are determined by the intersection of the demand and supply curves in a market."
                    ),
                    Triple(
                        "Market/Economic Equilibrium",
                        "A state in which the quantity demanded of a good or service equals the quantity supplied, resulting in no shortage or surplus.",
                        "Government intervention in the form of price controls can disrupt market equilibrium and lead to inefficient outcomes."
                    ),
                    Triple(
                        "Equilibrium Price",
                        "The price at which the quantity demanded of a good or service equals the quantity supplied, resulting in market equilibrium.",
                        "In a competitive market, the equilibrium price adjusts to balance supply and demand."
                    ),
                    Triple(
                        "Price Elasticity of Demand",
                        "A measure of the responsiveness of quantity demanded to changes in price, calculated as the percentage change in quantity demanded divided by the percentage change in price.",
                        "Products with elastic demand are more responsive to price changes, while products with inelastic demand are less responsive."
                    ),
                    Triple(
                        "Income Elasticity of Demand",
                        "A measure of the responsiveness of quantity demanded to changes in income, calculated as the percentage change in quantity demanded divided by the percentage change in income.",
                        "Luxury goods tend to have high income elasticity, while necessity goods have low income elasticity."
                    ),
                    Triple(
                        "Cross Elasticity of Demand",
                        "A measure of the responsiveness of quantity demanded of one good to changes in the price of another good, calculated as the percentage change in quantity demanded of one good divided by the percentage change in price of another good.",
                        "Complementary goods have negative cross elasticity, while substitute goods have positive cross elasticity."
                    ),
                    Triple(
                        "Market Structures",
                        "Different organizational and competitive characteristics of markets, including perfect competition, monopolistic competition, oligopoly, and monopoly.",
                        "Understanding market structures helps analyze pricing behavior, market power, and efficiency in different industries."
                    ),
                    Triple(
                        "Perfect Competition",
                        "A market structure characterized by many buyers and sellers, identical products, free entry and exit, and perfect information.",
                        "In perfect competition, firms are price takers and earn normal profits in the long run."
                    ),
                    Triple(
                        "Monopolistic Competition",
                        "A market structure characterized by many buyers and sellers, differentiated products, easy entry and exit, and some degree of market power.",
                        "Firms in monopolistic competition engage in non-price competition through product differentiation and marketing."
                    ),
                    Triple(
                        "Oligopoly",
                        "A market structure characterized by few large firms dominating the industry, high barriers to entry, interdependence among firms, and strategic interactions.",
                        "Oligopolistic firms engage in price competition, collusion, or non-price competition to gain market share."
                    ),
                    Triple(
                        "Population Growth on the Philippine Economy and Labor Market",
                        "The impact of population growth on economic development, labor supply, demand for goods and services, and resource allocation in the Philippines.",
                        "Rapid population growth in the Philippines presents challenges for infrastructure, education, healthcare, and employment opportunities."
                    ),
                    Triple(
                        "Labor Market",
                        "The market where workers supply their labor services to employers in exchange for wages or salaries.",
                        "Unemployment rates and wage levels in the labor market are influenced by factors such as labor supply, demand for goods and services, and government policies."
                    ),
                    Triple(
                        "Wage Situation in the Philippines",
                        "The prevailing wage rates, wage inequality, and factors affecting wage determination in the labor market in the Philippines.",
                        "Rising minimum wages and increasing demand for skilled workers contribute to improvements in the wage situation in the Philippines."
                    ),
                    Triple(
                        "Labor Migration and Overseas Filipino Workers (OFWs)",
                        "The movement of Filipino workers to other countries in search of better job opportunities and higher wages, resulting in remittances sent back to the Philippines.",
                        "Overseas Filipino Workers play a significant role in the Philippine economy by contributing to foreign exchange earnings and supporting their families through remittances."
                    ),
                    Triple(
                        "Philippine Economic Problems",
                        "Challenges and issues facing the Philippine economy, such as poverty, income inequality, unemployment, corruption, and inadequate infrastructure.",
                        "Addressing Philippine economic problems requires comprehensive policy measures to promote inclusive growth, improve governance, and enhance competitiveness."
                    ),
                    Triple(
                        "Philippine Peso Exchange Rates",
                        "The value of the Philippine peso relative to other currencies in the foreign exchange market, influenced by factors such as trade balances, interest rates, and investor sentiment.",
                        "A depreciation of the Philippine peso may boost exports but increase the cost of imports, affecting the country's trade balance and inflation rate."
                    ),
                    Triple(
                        "Rent",
                        "Payment made by a tenant to a landlord for the use of property or assets.",
                        "The company leased office space and paid monthly rent to the property owner."
                    ),
                    Triple(
                        "Minimum Wage",
                        "The lowest wage rate that employers are legally required to pay workers for their labor.",
                        "The government increased the minimum wage to improve the standard of living for low-income workers."
                    ),
                    Triple(
                        "Small, Medium, and Large scale Businesses",
                        "Different categories of businesses based on their size, revenue, number of employees, and market reach.",
                        "Small businesses play a vital role in job creation and economic development, while large-scale businesses often have greater resources and market dominance."
                    ),
                    Triple(
                        "Tools in Evaluating a Business",
                        "Methods, techniques, and frameworks used to assess the performance, viability, and potential of a business.",
                        "Financial ratio analysis, SWOT analysis, and market research are common tools in evaluating a business."
                    ),
                    Triple(
                        "SWOT Analysis",
                        "A strategic planning tool used to identify a business's Strengths, Weaknesses, Opportunities, and Threats.",
                        "Conducting a SWOT analysis helps businesses identify areas for improvement and develop effective strategies."
                    ),
                    Triple(
                        "Porter's Five Forces",
                        "A framework for analyzing the competitive forces and attractiveness of an industry, including the threat of new entrants, bargaining power of buyers and suppliers, threat of substitute products, and competitive rivalry.",
                        "Using Porter's Five Forces analysis, the company assessed the competitive landscape of the industry before entering the market."
                    ),
                    Triple(
                        "Industry Analysis",
                        "Examination of the economic, market, and competitive factors influencing a specific industry.",
                        "Industry analysis helps businesses understand market trends, competitive dynamics, and opportunities for growth."
                    ),
                    Triple(
                        "Environmental Analysis",
                        "Assessment of external factors and trends, such as political, economic, social, technological, environmental, and legal factors, that may impact a business.",
                        "Conducting an environmental analysis helps businesses anticipate changes and adapt their strategies accordingly."
                    ),
                    Triple(
                        "Consumer Theory",
                        "The study of how consumers make choices to allocate their limited resources among competing goods and services, based on preferences, budget constraints, and utility maximization.",
                        "Consumer theory examines consumer behavior and decision-making processes to understand demand patterns and market outcomes."
                    ),
                    Triple(
                        "Utility Function",
                        "A mathematical representation of a consumer's preferences for different combinations of goods and services, indicating the satisfaction or utility derived from consuming each combination.",
                        "Economists use utility functions to model consumer behavior and predict choices in response to changes in prices and incomes."
                    ),
                    Triple(
                        "The Production Theory",
                        "The study of how firms combine inputs, such as labor and capital, to produce output, based on technological constraints and production possibilities.",
                        "The production theory helps firms optimize production processes, minimize costs, and maximize profits."
                    ),
                    Triple(
                        "Socioeconomic Impact of a Business",
                        "The effects of a business's operations and activities on society, including economic, social, and environmental outcomes.",
                        "Assessing the socioeconomic impact of a business helps stakeholders understand its contributions and responsibilities within the community."
                    ),
                    Triple(
                        "Government Impact on Business",
                        "The influence of government policies, regulations, and actions on business operations, investment decisions, and market outcomes.",
                        "Changes in tax policies, trade regulations, and environmental laws can significantly impact business activities and profitability."
                    ),
                    Triple(
                        "Trade and Capital Movement",
                        "The exchange of goods, services, and capital across borders, facilitated by international trade and investment.",
                        "Globalization has led to increased trade and capital movement, creating opportunities for economic growth and development."
                    ),
                    Triple(
                        "Herfindahl-Hirschman Index (HHI)",
                        "A measure of market concentration in an industry, calculated by summing the squared market shares of all firms in the market.",
                        "Antitrust authorities use the Herfindahl-Hirschman Index to assess market competitiveness and identify potential monopolies or oligopolies."
                    ),
                    Triple(
                        "Elasticity of Supply",
                        "A measure of the responsiveness of quantity supplied to changes in price, calculated as the percentage change in quantity supplied divided by the percentage change in price.",
                        "The elasticity of supply helps determine how producers adjust their output in response to changes in market conditions."
                    ),
                    Triple(
                        "Consumer Surplus",
                        "The difference between what consumers are willing to pay for a good or service and what they actually pay, representing the benefit or surplus gained by consumers.",
                        "Consumer surplus increases when consumers can purchase goods at prices lower than their maximum willingness to pay."
                    ),
                    Triple(
                        "Producer Surplus",
                        "The difference between the price received by producers for a good or service and the minimum price they are willing to accept, representing the benefit or surplus gained by producers.",
                        "Producer surplus increases when producers can sell goods at prices higher than their minimum acceptable price."
                    ),
                    Triple(
                        "Market Failure",
                        "A situation where the allocation of resources by a free market is inefficient, leading to outcomes that are not Pareto optimal or socially desirable.",
                        "Externalities, public goods, and market power are common causes of market failure."
                    ),
                    Triple(
                        "Public Goods",
                        "Goods or services that are non-excludable and non-rivalrous, meaning that individuals cannot be excluded from their consumption, and one person's consumption does not diminish the availability to others.",
                        "National defense and public parks are examples of public goods provided by governments."
                    ),
                    Triple(
                        "Externality",
                        "A cost or benefit arising from the production or consumption of a good or service that is not reflected in the market price.",
                        "Pollution from factory emissions is a negative externality that imposes costs on society in the form of environmental damage and health hazards."
                    ),
                    Triple(
                        "Monopoly",
                        "A market structure characterized by a single seller dominating the industry, facing no competition, and having significant market power to set prices.",
                        "Monopolies can lead to higher prices, reduced output, and allocative inefficiency, resulting in consumer welfare losses."
                    ),
                    Triple(
                        "Monopsony",
                        "A market structure characterized by a single buyer dominating the industry, facing multiple sellers, and having significant market power to dictate prices and terms of trade.",
                        "Monopsonies can exploit suppliers by demanding lower prices for inputs, leading to inefficiency and reduced welfare for producers."
                    ),
                    Triple(
                        "Duopoly",
                        "A market structure characterized by two dominant firms competing in the industry, facing limited competition and strategic interactions.",
                        "Duopolies often engage in price competition, collusion, or differentiation strategies to gain market share and maximize profits."
                    ),
                    Triple(
                        "Game Theory",
                        "A mathematical framework used to analyze strategic interactions and decision-making among rational actors in competitive situations.",
                        "Game theory is used in economics to model behavior in markets, negotiations, and conflicts, predicting outcomes and strategies."
                    ),
                    Triple(
                        "Perfectly Competitive Market",
                        "A market structure characterized by many small firms producing identical products, free entry and exit, perfect information, and price-taking behavior.",
                        "In a perfectly competitive market, firms earn zero economic profit in the long run due to intense competition and efficient resource allocation."
                    ),
                    Triple(
                        "Natural Monopoly",
                        "A type of monopoly that arises when economies of scale allow a single firm to produce goods or services at a lower cost than multiple firms, leading to barriers to entry and the absence of competition.",
                        "Utilities such as water, electricity, and natural gas often exhibit natural monopoly characteristics due to high fixed costs and infrastructure requirements."
                    ),
                    Triple(
                        "Cartel",
                        "A formal agreement among firms in an oligopolistic industry to coordinate prices, output levels, or market shares to maximize collective profits.",
                        "Cartels are illegal in many countries due to their negative impact on competition and consumer welfare."
                    ),
                    Triple(
                        "Price Discrimination",
                        "A pricing strategy where a seller charges different prices to different customers for the same product or service, based on their willingness to pay, demand elasticity, or other characteristics.",
                        "Airline companies often practice price discrimination by offering different fares to passengers based on factors such as booking time, class, and flexibility."
                    ),
                    Triple(
                        "Arbitrage",
                        "The practice of exploiting price differences in different markets by buying low and selling high to make a profit, typically involving the simultaneous purchase and sale of assets or commodities.",
                        "Arbitrageurs profit from inefficiencies in financial markets by quickly buying undervalued assets in one market and selling them at a higher price in another market."
                    ),
                    Triple(
                        "Utility Maximization",
                        "The economic principle that individuals or firms seek to allocate their resources in a way that maximizes their satisfaction, well-being, or profit.",
                        "Consumers maximize utility by allocating their limited income to purchase goods and services that provide the highest level of satisfaction."
                    ),
                    Triple(
                        "Diminishing Marginal Utility",
                        "The principle that as a person consumes more units of a good or service, the additional satisfaction or utility derived from each additional unit decreases.",
                        "Diminishing marginal utility explains why consumers are willing to pay less for additional units of a product as they consume more of it."
                    ),
                    Triple(
                        "Market Segmentation",
                        "The process of dividing a market into distinct groups of buyers with different needs, characteristics, or behaviors, and developing tailored marketing strategies to target each segment effectively.",
                        "Companies use market segmentation to identify and target specific customer segments with customized products, pricing, and promotional campaigns."
                    ),
                    Triple(
                        "Disequilibrium",
                        "A situation in a market where the quantity demanded does not equal the quantity supplied, leading to imbalances in supply and demand and potential shortages or surpluses.",
                        "Price ceilings and floors can cause disequilibrium by creating artificial shortages or surpluses in the market."
                    ),
                    Triple(
                        "Economic Forecasting",
                        "The process of predicting future economic trends, such as GDP growth, inflation rates, and employment levels, based on historical data, statistical models, and economic indicators.",
                        "Economic forecasting helps businesses, policymakers, and investors make informed decisions and plan for the future."
                    ),
                    Triple(
                        "Market Segmentation",
                        "The process of dividing a market into distinct groups of buyers with different needs, characteristics, or behaviors, and developing tailored marketing strategies to target each segment effectively.",
                        "Companies use market segmentation to identify and target specific customer segments with customized products, pricing, and promotional campaigns."
                    ),
                    Triple(
                        "Cost-Benefit Analysis",
                        "A technique used to evaluate the potential benefits and costs of a project, investment, or policy by comparing the total expected benefits with the total expected costs.",
                        "Government agencies use cost-benefit analysis to assess the economic, social, and environmental impacts of proposed projects or regulations."
                    ),
                    Triple(
                        "Economic Policy",
                        "Government actions and interventions aimed at influencing the economy's performance, stability, and growth through monetary, fiscal, and regulatory measures.",
                        "Monetary policy tools include interest rate adjustments and open market operations, while fiscal policy measures include taxation and government spending."
                    ),
                    Triple(
                        "Inflation",
                        "A sustained increase in the general price level of goods and services in an economy over a period of time, leading to a decrease in the purchasing power of money.",
                        "Central banks aim to maintain price stability and target low and stable inflation rates to promote economic growth and stability."
                    ),
                    Triple(
                        "Deflation",
                        "A sustained decrease in the general price level of goods and services in an economy over a period of time, leading to an increase in the purchasing power of money.",
                        "Deflation can have negative consequences, such as lower consumer spending, increased debt burdens, and economic stagnation."
                    ),
                    Triple(
                        "Hyperinflation",
                        "An extremely high and rapid inflation rate, typically exceeding 50% per month, leading to a loss of confidence in the currency and economic instability.",
                        "Hyperinflation can result from excessive money supply growth, currency devaluation, or supply shocks, causing widespread economic hardship and social unrest."
                    ),
                    Triple(
                        "Economic Recession",
                        "A significant decline in economic activity, characterized by negative GDP growth, rising unemployment, and declining consumer spending and investment.",
                        "Government stimulus measures, such as increased government spending and tax cuts, are often used to mitigate the effects of a recession and stimulate economic recovery."
                    ),
                    Triple(
                        "Economic Depression",
                        "A severe and prolonged economic downturn, characterized by a deep contraction in economic activity, widespread unemployment, and financial crises.",
                        "The Great Depression of the 1930s is one of the most well-known examples of an economic depression, marked by bank failures, mass unemployment, and deflation."
                    ),
                    Triple(
                        "Supply Chain",
                        "The network of organizations, resources, activities, and technologies involved in the production, distribution, and sale of goods and services, from raw materials to the end consumer.",
                        "Efficient supply chain management is essential for minimizing costs, optimizing inventory levels, and meeting customer demand in a timely manner."
                    ),
                    Triple(
                        "Globalization",
                        "The process of increasing interconnectedness and interdependence among countries, economies, and societies through international trade, investment, and information exchange.",
                        "Globalization has led to increased cross-border flows of goods, services, capital, and technology, contributing to economic growth, cultural exchange, and geopolitical challenges."
                    ),
                    Triple(
                        "Economic Growth",
                        "The increase in a country's real GDP over time, reflecting expansion in production, consumption, and investment.",
                        "Sustainable economic growth is essential for improving living standards, reducing poverty, and achieving long-term prosperity."
                    ),
                    Triple(
                        "Capitalism",
                        "An economic system characterized by private ownership of the means of production, free markets, competition, and profit maximization.",
                        "Capitalism incentivizes innovation, entrepreneurship, and efficiency but can also lead to income inequality and market failures that require government intervention."
                    ),
                    Triple(
                        "Socialism",
                        "An economic system characterized by collective ownership of the means of production, central planning, and distribution of resources based on social welfare objectives.",
                        "Socialist economies prioritize social equity, public ownership, and state intervention in markets to address income inequality and ensure basic needs are met."
                    ),
                    Triple(
                        "Market Failure",
                        "A situation where the allocation of resources by a free market is inefficient, leading to outcomes that are not Pareto optimal or socially desirable.",
                        "Externalities, public goods, and market power are common causes of market failure."
                    ),
                    Triple(
                        "Perfect Competition",
                        "A market structure characterized by many small firms producing identical products, free entry and exit, perfect information, and price-taking behavior.",
                        "In a perfectly competitive market, firms earn zero economic profit in the long run due to intense competition and efficient resource allocation."
                    ),
                    Triple(
                        "Monopolistic Competition",
                        "A market structure characterized by many buyers and sellers, differentiated products, easy entry and exit, and some degree of market power.",
                        "Firms in monopolistic competition engage in non-price competition through product differentiation and marketing."
                    ),
                    Triple(
                        "Oligopoly",
                        "A market structure characterized by few large firms dominating the industry, high barriers to entry, interdependence among firms, and strategic interactions.",
                        "Oligopolistic firms engage in price competition, collusion, or non-price competition to gain market share."
                    ),
                    Triple(
                        "Monopoly",
                        "A market structure characterized by a single seller dominating the industry, facing no competition, and having significant market power to set prices.",
                        "Monopolies can lead to higher prices, reduced output, and allocative inefficiency, resulting in consumer welfare losses."
                    ),
                    Triple(
                        "Fiscal Policy",
                        "Government's use of taxation and spending to influence the economy, particularly to stabilize economic fluctuations and promote growth.",
                        "During a recession, the government may implement expansionary fiscal policy by increasing spending or cutting taxes to stimulate demand."
                    ),
                    Triple(
                        "Monetary Policy",
                        "The management of money supply and interest rates by a central bank to achieve macroeconomic objectives such as price stability, full employment, and economic growth.",
                        "Central banks use tools like open market operations and interest rate adjustments to implement monetary policy and control inflation."
                    ),
                    Triple(
                        "Central Bank",
                        "An institution responsible for overseeing a nation's monetary system, controlling the money supply, and regulating financial institutions to achieve macroeconomic objectives.",
                        "The Federal Reserve is the central bank of the United States, responsible for conducting monetary policy and promoting financial stability."
                    ),
                    Triple(
                        "Inflation Targeting",
                        "A monetary policy framework where central banks set explicit inflation targets and adjust policy instruments to achieve and maintain those targets over the medium term.",
                        "The European Central Bank and the Bank of England are examples of central banks that use inflation targeting as part of their monetary policy framework."
                    ),
                    Triple(
                        "Quantitative Easing (QE)",
                        "A monetary policy tool used by central banks to stimulate the economy by purchasing financial assets, such as government bonds and mortgage-backed securities, to increase the money supply and lower long-term interest rates.",
                        "During the global financial crisis, central banks implemented quantitative easing measures to provide liquidity to financial markets and support economic recovery."
                    ),
                    Triple(
                        "Expansionary Monetary Policy",
                        "A monetary policy strategy aimed at increasing the money supply, lowering interest rates, and stimulating economic activity to combat recession or deflationary pressures.",
                        "The central bank may implement expansionary monetary policy by lowering the target interest rate and purchasing government securities."
                    ),
                    Triple(
                        "Contractionary Monetary Policy",
                        "A monetary policy strategy aimed at reducing the money supply, raising interest rates, and slowing down economic activity to control inflation or prevent asset bubbles.",
                        "The central bank may implement contractionary monetary policy by raising the target interest rate and selling government securities."
                    ),
                    Triple(
                        "Discount Rate",
                        "The interest rate charged by a central bank on loans provided to commercial banks and other financial institutions as part of its monetary policy operations.",
                        "A reduction in the discount rate encourages borrowing and stimulates economic activity, while an increase in the discount rate tightens credit conditions and slows down lending."
                    ),
                    Triple(
                        "Open Market Operations",
                        "The buying and selling of government securities (bonds) by a central bank in the open market to influence the money supply, interest rates, and liquidity in the banking system.",
                        "By purchasing government bonds, the central bank injects liquidity into the banking system, lowering interest rates and stimulating lending and investment."
                    ),
                    Triple(
                        "Money Supply",
                        "The total amount of money circulating in the economy, including currency in circulation, demand deposits, and other liquid assets.",
                        "Changes in the money supply affect interest rates, inflation, and economic activity, making it a key focus of monetary policy."
                    ),
                    Triple(
                        "Liquidity Trap",
                        "A situation where nominal interest rates are very low, and the demand for money becomes highly elastic, rendering monetary policy ineffective in stimulating economic activity.",
                        "In a liquidity trap, despite low interest rates, consumers and businesses hold onto cash rather than spending or investing, leading to stagnant economic growth."
                    ),
                    Triple(
                        "Interest Rate",
                        "The cost of borrowing money or the return on savings and investments, expressed as a percentage of the principal amount.",
                        "A decrease in interest rates stimulates borrowing and spending, while an increase in interest rates encourages saving and investment."
                    ),
                    Triple(
                        "Inflation Rate",
                        "The rate at which the general level of prices for goods and services is rising over a period of time, typically measured as an annual percentage increase.",
                        "Central banks aim to maintain low and stable inflation rates to promote economic stability and purchasing power."
                    ),
                    Triple(
                        "Money Demand",
                        "The desired holding of financial assets in the form of money (cash and deposits) by households and firms for transactions, precautionary, and speculative purposes.",
                        "Changes in interest rates, income levels, and inflation expectations influence money demand, affecting the effectiveness of monetary policy."
                    ),
                    Triple(
                        "Money Multiplier",
                        "The ratio of the increase in the money supply to the increase in the monetary base, reflecting the expansionary effect of fractional reserve banking on the money supply.",
                        "The money multiplier determines the extent to which changes in the monetary base by the central bank translate into changes in the broader money supply."
                    ),
                    Triple(
                        "Reserve Requirement",
                        "The percentage of deposits that banks are required to hold in reserve, either as cash or as deposits with the central bank, to ensure liquidity and stability in the banking system.",
                        "A decrease in the reserve requirement allows banks to lend out more of their deposits, increasing the money supply and stimulating economic activity."
                    ),
                    Triple(
                        "Velocity of Money",
                        "The rate at which money circulates or changes hands in the economy in a given period, calculated as the ratio of nominal GDP to the money supply.",
                        "Changes in the velocity of money affect aggregate demand and inflation, influencing the effectiveness of monetary policy in achieving macroeconomic objectives."
                    ),
                    Triple(
                        "Nominal Interest Rate",
                        "The stated interest rate on a financial instrument or loan, not adjusted for inflation or changes in purchasing power over time.",
                        "Borrowers and lenders use nominal interest rates to calculate the cost of borrowing or the return on investments, without considering the effects of inflation."
                    ),
                    Triple(
                        "Real Interest Rate",
                        "The nominal interest rate adjusted for inflation, representing the true cost of borrowing or the real return on savings and investments.",
                        "Investors and policymakers use real interest rates to assess the purchasing power of money and the opportunity cost of holding financial assets."
                    ),
                    Triple(
                        "Moral Suasion",
                        "The informal influence exerted by a central bank or regulatory authority on banks and financial institutions to encourage compliance with monetary policy objectives or regulatory standards.",
                        "Central banks may use moral suasion to persuade banks to implement specific lending practices, reserve requirements, or interest rate adjustments."
                    ),
                    Triple(
                        "Base Money",
                        "The total amount of money in circulation, consisting of currency (banknotes and coins) held by the public and reserves held by commercial banks at the central bank.",
                        "Changes in base money by the central bank affect the broader money supply through the money multiplier process, influencing economic activity and inflation."
                    ),
                    Triple(
                        "Quantitative Tightening (QT)",
                        "The process of reducing the size of a central bank's balance sheet and withdrawing liquidity from the financial system by selling government securities and other assets.",
                        "Quantitative tightening is used as a contractionary monetary policy tool to control inflation, stabilize financial markets, and prevent asset bubbles."
                    ),
                    Triple(
                        "Accounts Receivable",
                        "Money owed to a company by its customers for goods or services that have been delivered or used but not yet paid for.",
                        "Proper management of accounts receivable is crucial for maintaining a company's cash flow and liquidity."
                    ),
                    Triple(
                        "Accrual Basis Accounting",
                        "An accounting method that records revenue and expenses when they are earned or incurred, regardless of when the cash is received or paid.",
                        "Accrual basis accounting provides a more accurate representation of a company's financial performance by matching revenues with related expenses."
                    ),
                    Triple(
                        "Amortization",
                        "The process of spreading out the cost of an intangible asset or a loan over its useful life or the loan repayment period.",
                        "The monthly mortgage payment includes both principal and interest, with the interest portion being amortized over the life of the loan."
                    ),
                    Triple(
                        "Break-Even Analysis",
                        "A technique used to determine the point at which total revenue equals total costs, and the business does not make a profit or loss.",
                        "Conducting a break-even analysis can help businesses determine the minimum level of sales required to cover their fixed and variable costs."
                    ),
                    Triple(
                        "Capital Budgeting",
                        "The process of evaluating potential investments or expenditures that are expected to generate benefits over a long period of time.",
                        "Capital budgeting techniques, such as net present value and internal rate of return, are used to assess the profitability of potential projects."
                    ),
                    Triple(
                        "Depreciation",
                        "The gradual allocation of the cost of a tangible asset over its useful life, reflecting its decrease in value due to wear and tear or obsolescence.",
                        "Depreciation is a non-cash expense that allows businesses to recover the cost of assets used in generating revenue."
                    ),
                    Triple(
                        "Working Capital",
                        "The difference between a company's current assets and current liabilities, representing the funds available for short-term operations and obligations.",
                        "Maintaining a healthy level of working capital is essential for businesses to meet their short-term financial obligations and fund their day-to-day operations."
                    ),

                    )

                val insertQuery =
                    "INSERT INTO dictionary_terms (term, definition, example) VALUES (?, ?, ?)"
                val insertStatement = db.compileStatement(insertQuery)

                for (termData in termsToInsert) {

                    insertStatement.bindString(1, termData.first)
                    insertStatement.bindString(2, termData.second)
                    insertStatement.bindString(3, termData.third)
                    insertStatement.executeInsert()

                }

                db.setTransactionSuccessful()
            } finally {
                db.endTransaction()
            }
        }
    }

    private fun checkIfDatabaseIsPopulated(db : SQLiteDatabase) : Boolean {
        val query = "SELECT COUNT(*) FROM dictionary_terms"
        val cursor = db.rawQuery(query, null)
        var isPopulated = false
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                val count = cursor.getInt(0)
                isPopulated = count > 0
            }
            cursor.close()
        }
        return isPopulated
    }

    /**
    private fun isTermAlreadyExists(db : SQLiteDatabase, term : String) : Boolean {
    val query = "SELECT COUNT(*) FROM dictionary_terms WHERE term = ?"
    val cursor = db.rawQuery(query, arrayOf(term))
    var isExists = false
    if (cursor != null) {
    if (cursor.moveToFirst()) {
    val count = cursor.getInt(0)
    isExists = count > 0
    }
    cursor.close()
    }
    return isExists
    } */
}