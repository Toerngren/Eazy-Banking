package businessLogic.User;

public class KYC {
   private final String personalNumber;
   private final String occupation;
   private final double salary;
   private final boolean pep;
   private final boolean fatca;

   public KYC (String personalNumber, String occupation, double salary, boolean pep, boolean fatca) {
      this.personalNumber = personalNumber;
      this.occupation = occupation;
      this.salary = salary;
      this.pep = pep;
      this.fatca = fatca;
   }

   public String toString(){
      return "Personal number: " + personalNumber + System.lineSeparator() + "Occupation: " + occupation + System.lineSeparator() + "Salary: " + salary + System.lineSeparator() + "PEP: " +  pep + System.lineSeparator() +
              "FATCA: "+fatca + System.lineSeparator();
   }


   public boolean isFatca() {
      return fatca;
   }

   public boolean isPep() {
      return pep;
   }

   public String getOccupation() {
      return occupation;
   }

   public double getSalary() {
      return salary;
   }


   public String getPersonalNumber() {
      return personalNumber;
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) return true;
      if (!(o instanceof KYC)) return false;
      KYC kyc = (KYC) o;
      if (pep != kyc.pep) return false;
      if (fatca != kyc.fatca) return false;
      if (Double.compare(kyc.salary, salary) != 0) return false;
      return occupation.equals(kyc.occupation);
   }

   @Override
   public int hashCode() {
      int result;
      long temp;
      result = occupation.hashCode();
      result = 31 * result + (pep ? 1 : 0);
      result = 31 * result + (fatca ? 1 : 0);
      temp = Double.doubleToLongBits(salary);
      result = 31 * result + (int) (temp ^ (temp >>> 32));
      return result;
   }
}
