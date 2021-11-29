package businessLogic;

public class KYC {

   private String occupation;
   private double salary;
   private boolean pep;
   private boolean fatca;
   private boolean approved;

   public KYC (String occupation, double salary, boolean pep, boolean fatca, boolean approved) {
      this.occupation = occupation;
      this.salary = salary;
      this.pep = pep;
      this.fatca = fatca;
      this.approved = approved;
   }

   public String toString(){
      return "Occupation: " + occupation + "Salary: " + salary + " Pep: " +  pep + " FATCA: "+fatca + " Approved: " + approved;
   }

   public void setSalary(double salary) {
      this.salary = salary;
   }

   public boolean isFatca() {
      return fatca;
   }

   public boolean isPep() {
      return pep;
   }

   public void setPep(boolean pep) {
      this.pep = pep;
   }

   public void setFatca(boolean fatca) {
      this.fatca = fatca;
   }

   public String getOccupation() {
      return occupation;
   }

   public double getSalary() {
      return salary;
   }

   public void setApproved(boolean approved) {
      this.approved = approved;
   }

   public boolean isApproved() {
      return approved;
   }

   public void setOccupation(String occupation) {
      this.occupation = occupation;
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
