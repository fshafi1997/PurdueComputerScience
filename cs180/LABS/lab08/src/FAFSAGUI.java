import javax.swing.*;

/**
 * Created by farhanshafi on 10/18/16.
 */
public class FAFSAGUI {
    public static void main(String[] args) {
        boolean proceedClass = true;
        do {
            //1
            JOptionPane.showMessageDialog(null, "Welcome to FAFSA", "Welcome", JOptionPane.INFORMATION_MESSAGE);

            //2
            boolean isAccepted;
            int accepted = JOptionPane.showOptionDialog(null, "Have you been accepted in a degree or certificate program", "Program Acceptance", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                    null, null, null);
            if (accepted == JOptionPane.YES_OPTION) {
                isAccepted = true;
            } else isAccepted = false;

            //3
            boolean isRegistered;
            int registered = JOptionPane.showOptionDialog(null, "Are you registered for the selected service?", "Selective Service", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                    null, null, null);
            if (registered == JOptionPane.YES_OPTION) {
                isRegistered = true;
            } else isRegistered = false;

            //4
            boolean hasASsn;
            int hasSSN = JOptionPane.showOptionDialog(null, "Do you have a security number?", "Social Security Number", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                    null, null, null);
            if (hasSSN == JOptionPane.YES_OPTION) {
                hasASsn = true;
            } else hasASsn = false;

            //5
            boolean hasAResidency;
            int hasResidency = JOptionPane.showOptionDialog(null, "Do you have valid residency status?", "Residency Status", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                    null, null, null);
            if (hasResidency == JOptionPane.YES_OPTION) {
                hasAResidency = true;
            } else hasAResidency = false;

            boolean forLOOP1 = true;
            //6
            String age;
            do {
                age = JOptionPane.showInputDialog(null, "How old are you?", "Age", JOptionPane.QUESTION_MESSAGE);
                if (Integer.parseInt(age) < 0) {
                    JOptionPane.showMessageDialog(null, "Age cannot be a negative number", "Error:Age", JOptionPane.ERROR_MESSAGE);
                } else {
                    forLOOP1 = false;
                }
            } while (forLOOP1);
            int age1 = Integer.parseInt(age);
            forLOOP1 = true;

            //7
            String creditHours;
            do {
                creditHours = JOptionPane.showInputDialog(null, "How many credit hours do you plan on taking?", "Credit hours", JOptionPane.QUESTION_MESSAGE);
                if (Integer.parseInt(creditHours) < 1 || Integer.parseInt(creditHours) > 24) {
                    JOptionPane.showMessageDialog(null, "Credit hours must be between 1 and 24 (inclusive)", "Error:Credit Hours", JOptionPane.ERROR_MESSAGE);
                } else {
                    forLOOP1 = false;
                }
            } while (forLOOP1);
            int creditHours1 = Integer.parseInt(creditHours);
            forLOOP1 = true;

            //8
            String Sincome;
            do {
                Sincome = JOptionPane.showInputDialog(null, "What is your total yearly income?", "Student Income", JOptionPane.QUESTION_MESSAGE);
                if (Double.parseDouble(Sincome) < 0) {
                    JOptionPane.showMessageDialog(null, "Input cannot be a negative number", "Error:Student Income", JOptionPane.ERROR_MESSAGE);
                } else {
                    forLOOP1 = false;
                }
            } while (forLOOP1);
            double Sincome1 = Double.parseDouble(Sincome);
            forLOOP1 = true;

            //9
            String Pincome;
            do {
                Pincome = JOptionPane.showInputDialog(null, "What is your parent's total yearly income?", "Parent Income", JOptionPane.QUESTION_MESSAGE);
                if (Double.parseDouble(Pincome) < 0) {
                    JOptionPane.showMessageDialog(null, "Input cannot be a negative number", "Error:Parent Income", JOptionPane.ERROR_MESSAGE);
                } else {
                    forLOOP1 = false;
                }
            } while (forLOOP1);
            double Pincome1 = Double.parseDouble(Pincome);
            forLOOP1 = true;

            //10
            boolean isDependent;
            int dependent = JOptionPane.showOptionDialog(null, "Are you a dependent?", "Dependency", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                    null, null, null);
            if (dependent == JOptionPane.YES_OPTION) {
                isDependent = true;
            } else isDependent = false;

            //11
            String standing = "";
            String[] values = {"Freshmen", "Sophomore", "Junior", "Senior", "Graduate"};
            String response = (String) JOptionPane.showInputDialog(null, "What is your current class standing", "Class Standing", JOptionPane.PLAIN_MESSAGE,
                    null, values, null);
            if (response.equalsIgnoreCase("freshmen") || response.equalsIgnoreCase("sophomore") || response.equalsIgnoreCase("junior") || response.equalsIgnoreCase("senior")) {
                standing = "Undergraduate";
            } else if (response.equalsIgnoreCase("graduate")) {
                standing = "Graduate";
            }

            //12
            FAFSA fafsa1 = new FAFSA(isAccepted, isRegistered, hasASsn, hasAResidency, isDependent, age1, creditHours1, Sincome1, Pincome1, standing);

            //13
            double loan = fafsa1.calcStaffordLoan();
            double grant = fafsa1.calcFederalGrant();
            double workStudy = fafsa1.calcWorkStudy();
            double totalAwards = fafsa1.calcFederalAidAmount();

            //14
            JOptionPane.showMessageDialog(null, "Loans: $" + loan + "\n" + "Grants: $" + grant + "\n" + "WorkStudy: $" + workStudy + "\n" +
                    "\n" + "----____----" + "\n" + "\n" + "Total: $" + totalAwards, "FAFSA Results", JOptionPane.INFORMATION_MESSAGE, null);

            //15
            int proceed = JOptionPane.showOptionDialog(null, "Would you like to complete another application?", "Continue", JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE, null, null, null);
            if (proceed == JOptionPane.YES_OPTION) {
                proceedClass = true;
            } else proceedClass = false;
        } while (proceedClass = true);
    }
}
