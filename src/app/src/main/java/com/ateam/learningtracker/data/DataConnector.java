package com.ateam.learningtracker.data;

import android.util.Log;

import com.orm.SugarDb;
import com.orm.util.NamingHelper;

import java.util.ArrayList;
import java.util.List;

public class DataConnector {
    public static List<SubjectProgress> getSubjectsProgressInfo() {
        return getSubjectsProgressInfo(0);
    }

    /**
     * Get progress info in certain time period.
     * @param periodStartTime period start time (unix timestamp)
     * @return
     */
    public static List<SubjectProgress> getSubjectsProgressInfo(long periodStartTime) {
        List<SubjectEntity> allSubjects = SubjectEntity.find(SubjectEntity.class, "active = ?", "1");
        List<SubjectProgress> subjectProgresses = new ArrayList<>();

        for (SubjectEntity subject:allSubjects) {
            SubjectProgress sp = new SubjectProgress();
            sp.name = subject.name;
            sp.overallProgress = getSubjectProgress(subject, periodStartTime);

            subjectProgresses.add(sp);
        }

        return subjectProgresses;
    }

    /**
     * Creates some basic subjects, subsections etc for testing purposes
     */
    public static void initDbData() {
        long subjectsCount = SubjectEntity.count(SubjectEntity.class, "1", null);
        if (subjectsCount != 0) {
            // uncomment this to production
            // return;
            LearningSessionEntity.deleteAll(LearningSessionEntity.class);
            SubsectionEntity.deleteAll(SubsectionEntity.class);
            SubjectEntity.deleteAll(SubjectEntity.class);
        }

        String[] subjects = new String[]{"Accounting ", "Agricultural Science ", "Art ", "Biology ", "Business ", "Chemistry ", "Design & Communication Graphics ", "Economics ", "English ", "French ", "Geography ", "History  ", "Mathematics ", "Physics ", "Spanish "};
        String[][] subsections = new String[][]{
                new String[]{"Budgeting-Flexible", "Depreciation of Fixed Assets", "Fixed Assets Valuation", "Costing-Job, Product, Stock Valuation & O/H Apportionment", "Farm Accounts", "Service Firms", "Published Accounts", "Correction of Errors/ Suspense", "Interpretation of Accounts", "Budgeting- Production", "Costing-Marginal", "Incomplete Records B", "Incomplete Records A", "Control Accounts", "Final Accounts- Company", "Club Accounts", "Cash Flow Statements", "Revaluation of Fixed Assets", "Tabular Statements", "Final Accounts- Manufacturing", "Budgeting- Cash", "Final Accounts- Sole Trader"},
                new String[]{"Fertilisers, Pollution & the Environment", "Give a scientific explanation for...", "Soil Science", "Genetics", "IDENTIFICATIONS - Plants", "Plant Physiology", "IDENTIFICATIONS - Animals", "PROJECT", "Animal Production (All animals)", "Animal Diseases", "Pigs", "Beef and Dairy Cattle", "Animal Physiology", "Grassland", "Describe an Experiment...", "Sheep", "Classification of Organisms", "Crop Production"},
                new String[]{"Appreciation - Graphic Design", "Europe - Surrealism/Cubism", "Appreciation - Product Design", "Pre-Christian - Burial and monastic sites", "Europe - Medieval", "Ireland - Modern Artists and Designers", "Early Christian Ireland - Manuscripts", "Europe - Gothic & Romanesque Architecture", "Ireland - Romantic Period", "Ireland - Georgian Architecture and Arts", "Europe - Post Impressionism", "Appreciation - Architecture and Renovation", "Europe - Romantic Period", "Europe - Impressionist", "Appreciation - Film Studies", "Generic Qs - Categories", "Pre Christian - Iron Age", "Early Christian Ireland - Metalwork", "Generic Qs - Movements", "Early Christian Ireland - Stonework", "Appreciation - Gallery Visit", "Pre Christian - Bronze Age", "Europe - Renaissance"},
                new String[]{"Food & Food tests", "Musculoskeletal System", "Eye/Ear & Nervous system", "Blood & Circulatory System", "Genetics, DNA & Evolution", "Respiration", "Plant Responses & Seed growth", "Human Reproduction", "Breathing System", "Plant Transport and Osmosis", "Digestive System", "The Scientific Method", "Plant Structure", "Plant Reproduction", "Bacteria, Viruses, Fungi + Yeast", "Excretion", "Photosynthesis", "Cell Division", "Hormonal/Endrocrine System", "Ecology & Ecosystems", "Immune system", "Cell Structure", "Experiment Questions", "Cell Metabolism & Enzymes"},
                new String[]{"3. Communication & I.T. in Business", "5. Marketing", "6. Ethics & the Environment in Business", "7. The EU: Institutions & policies", "5. Finance in Business", "4. Business Accounts & Ratio Analysis", "4. Taxation", "6. Types of Business: Sole Trader, etc. ", "ABQ 2019 (Units 1,2,&3)", "4. Insurance", "4. Monitoring a Business", "1. People In Business", "2. Entrepreneurs & Enterprise", "7. International Trade & Business", "ABQ 2020 (Units 2,3&4)", "5. Starting a Business/ Business Plans", "3. Management Skills & Activities", "6. Sectors: Primary, Secondary, Tertiary", "1. Industrial Relations & Contracts", "6. NGOs and Community Development ", "4. Human Resource Management", "1. Consumer Protection & Legislation", "5. Business Expansion"},
                new String[]{"Organic Chemistry", "Instrumentation & Chromatography", "Gas Laws, moles & Gas Properties", "Radioactivity", "Electron Arrangement", "Fuels & Thermochemistry", "Chemical Equilibrium", "Periodic Table", "Water & Water Analysis", "Ionic & Covalent Bonding", "Option: Industrial Chemistry", "Oxidation & Reduction", "Atomic Structure", "Option: Materials & Polymers", "Acids, Bases & PH calculations", "Option: Metals", "Rates of Reaction", "Option: Atmospheric Chemistry", "Stoichiometry, Formulae & Equations", "Experiment Questions"},
                new String[]{"The Oblique Plane", "Hyperbolic Paraboloid", "Orthographic & Auxiliary Projection", "Rotation & Inclination of Solids", "Hyperboloid of Revolution", "Solids in Contact", "Assemblies", "Intersection of Solids", "Skew Lines", "Developments & Envelopments", "Surface Geometry", "Dynamic Mechanisms", "Cartesian Coordinates", "Geological Geometry", "Structural Forms", "Axo/Di/Tri-metric Projection", "Conic Sections", "Perspective"},
                new String[]{"The Consumer", "Production & Consumption", "Costs of Production - Land", "National income", "Costs of Production - Labour", "Formulas", "The Government in the economy", "Economists (Kenyes, Smith, Marx...)", "Inflation", "Factor Incomes: Rent,  Profit, Wages..", "Market Structures", "International trade & Payments", "Economic Growth & Development", "Demand & Supply", "The Irish Economy", "Economic Policies: Problems & Conflict", "Elasticity", "Taxes & Taxation", "Costs of Production - Enterprise", "Money & Banking", "Employment", "Costs of Production - Capital"},
                new String[]{"King Lear", "Poetry - Ni Chuilleanain (2019)/(2020)", "Poetry - Kennelly (2019)", "Poetry - Dickinson (2020)", "Poetry - Bishop (2019)", "1 Reading Comprehension", "Comparative - Vision & Viewpoint (2019)", "Text - All My Sons", "Macbeth (2019)", "Poetry - Heaney (2019)", "Poetry - Wordsworth (2020)", "Poetry - Boland (2020)", "Text - Americanah", "Comparative - Cultural Context (2018 only)", "Text - The Handmaid's Tale", "1 Composition (Personal Writing)", "Unseen Poetry", "The Tempest (2020)", "Poetry - Lawrence (2019)/(2020)", "Poetry - Frost (2020)", "Poetry - Rich (2020)", "Comparative - Theme or Issue (2019)", "Hamlet (2020)", "Text - Wuthering Heights ", "Text - The Great Gatsby ", "Comparative - Literary genre (2019)", "Text - Persuasion ", "Text - By the Bog of Cats (2019 only)", "Poetry - Plath (2019)", "Poetry - Durcan (2020)", "Poetry - Hopkins (2019)", "Poetry - Yeats (2019)"},
                new String[]{"Opinion - Youth, teenagers and families", "ORAL exam", "Write a Note...", "AURAL - Interview ", "Opinion - Animals, nature and the environment", "AURAL - Conversation", "Written Production (récit)", "Literary/Novel Comprehension", "Write an eMail...", "Opinion - Politics, social issues and equality", "Opinion - Ireland, travel, tourism and culture", "Opinion - Technology and media", "AURAL - News Pieces", "Write a Diary...", "Newspaper/Magazine Comprehension", "Write a letter...", "Opinion - Education", "Opinion - Sport", "Opinion - Health"},
                new String[]{"Weather & Climate", "Economic - Energy", "Option - Geoecology", "Option - Atmosphere & Ocean", "Regions - Continental", "Economic - Multinationals & Globalisation", "Plate Tectonics", "Mapwork", "The Sea", "Aerial photos", "Human - Urban Geography", "Tables/Graphs Questions", "Elective - Economic", "Glaciation", "Human - Population", "Earthquakes & Volcanoes", "Regions - European", "Economic - European Union", "Urban Land uses", "Karst Regions", "Option - Global Interdependence", "Rocks, Weathering & Mass Movement", "Economic - Enviromental impact", "Regions - Ireland", "Economic - Developing Economies", "PROJECT - Investigation", "Rivers", "Option - Culture & Identity", "Elective - Human"},
                new String[]{"3.IRL Sovereignty & the impact of partition", "1.IRL Ireland & the Union", "4.IRL The Irish diaspora", "EUR6. The United States & the World", "EUR5. Retreat from Empire & aftermath", "2.IRL Movements for political/social reform", "6.IRL Republic - government, society, economy", "EUR2. Nation states & international tensions", "EUR4. Division & Realignment", "EUR3. Dictatorship & Democracy", "EUR1. Nationalism & state formation", "5.IRL Politics & Society in Northern Ireland"},
                new String[]{"Functions", "Logs", "Probability", "Algebra", "Geometry", "Complex Numbers", "Roots of a Function/ Equation", "Statistics & Data", "Integration", "The Circle", "Differentiation", "Financial Maths/Arithmetic", "Geometry (Proofs and Constructions)", "Sets", "Area & Volume", "Graphs of Functions", "Induction", "Trigonometry", "Co-Ordinate Geometry, The Line", "Sequences & Series"},
                new String[]{"Acceleration", "Vibration & Sound", "Reflection & Mirrors", "Electric Circuits", "Applied Electricity", "Waves & Wave Motion", "Current & Charge", "Electromagnetic Induction", "Speed, Displacement, Velocity", "Work, Energy & Power", "Exp. Qs. -  Heat", "Exp. Qs. -  Mechanics", "Exp. Qs. -  Electricity", "Static Electricity", "The Electron", "Heat & Heat Transfer", "Exp. Qs (all)", "Pressure, Gravity & Moments", "Potential Difference & Capacitance", "Light", "Refraction & Lenses", "Vectors & Scalars", "Particle Physics", "Exp. Qs. -  Sound", "Force,Mass & Momentum", "The Atom, Nucleus & Radioactivity", "Magnets and Magnetic Fields", "Exp. Qs. -  Light", "Circular Motion", "Semiconductors", "Nuclear Energy", "Resistance", "Temperature & Thermometers", "Simple Harmonic Motion"},
                new String[]{"ORAL exam", "Aural - Dialogo/ Dialogue", "Aural - Una Noticia/News", "Write a Dialogue...", "Writer a Diary entry/note...", "Journalistic Text", "Write a Letter/email...", "Opinion Text", "Aural - Anuncio/ Announcement", "Short Comprehensions", "Prescribed Literature (Sin Noticias de Gurb)", "Aural - El Tiempo/ Weather", "Aural - Descriptivo/ Description"},
        };

        for (int i = 0; i < subjects.length; i++) {
            String subjectName = subjects[i];
            // Uncomment this for debug purposes
            // SubjectEntity subjectEntity = new SubjectEntity(subjectName, (int) (Math.random() * 100), Math.random() > 0.5 );
            SubjectEntity subjectEntity = new SubjectEntity(subjectName, 50, false);
            subjectEntity.save();

            String[] subs = subsections[i];

            for (String subName:subs) {
                SubsectionEntity subsection = new SubsectionEntity(subName, 0.1f, subjectEntity);
                subsection.save();

                /* Uncomment this for debug purposes

                int numberOfSessions = (int) (Math.random() * 5);

                for (int j = 0; j < numberOfSessions; j++) {
                    long timestamp = System.currentTimeMillis() / 1000 - (long)(Math.random() * 3600 * 24 * 30);
                    LearningSessionEntity session = new LearningSessionEntity(timestamp - (int)(Math.random() * 3600 * 5), timestamp, "nope," + j, subsection);
                    session.save();
                } */
            }
        }
    }

    public static SubjectEntity getSubjectByName(String name) {
        List<SubjectEntity> subjects = SubsectionEntity.find(SubjectEntity.class, "name = ?", name);
        return subjects.size() > 0 ? subjects.get(0) : null;
    }

    public static SubsectionEntity getSubsectionByName(String name, SubjectEntity subject) {
        List<SubsectionEntity> subs = SubsectionEntity.find(SubsectionEntity.class, "name = ? and subject = ?", name, subject.getId().toString());
        return subs.size() > 0 ? subs.get(0) : null;
    }

    private static float getSubjectProgress(SubjectEntity subject) {
        return getSubjectProgress(subject, 0);
    }

    private static float getSubjectProgress(SubjectEntity subject, long periodStartTime) {

        List<SubsectionEntity> allSubjectSubs = SubsectionEntity.find(SubsectionEntity.class, "subject = ?", subject.getId().toString());
        float progress = 0;
        float importancySum = 0;
        for (SubsectionEntity sub:allSubjectSubs) {
            importancySum += sub.importancy;
        }

        for (SubsectionEntity sub:allSubjectSubs) {
            progress += DataConnector.getSubsectionProgress(sub, subject, importancySum, periodStartTime);
        }
        progress /= 4;

        return progress;
    }

    public static float getSubsectionProgress(SubsectionEntity subsection, SubjectEntity subject, float importancySum) {
        return getSubsectionProgress(subsection, subject, importancySum, 0);

    }

    public static float getSubsectionProgress(SubsectionEntity subsection, SubjectEntity subject, float importancySum, long periodStartTime) {

        List<LearningSessionEntity> allSessions = LearningSessionEntity.find(LearningSessionEntity.class,
                "subsection = ? and time_start > ?", subsection.getId().toString(), String.valueOf(periodStartTime));
        float secondsSum = 0;
        for(LearningSessionEntity session: allSessions) {
            secondsSum += session.timeEnd - session.timeStart;
        }

        float hours = secondsSum / 3600;

        float timeForImportancy = subject.studyTime / importancySum;

        return hours / (subsection.importancy * timeForImportancy);

    }


}
