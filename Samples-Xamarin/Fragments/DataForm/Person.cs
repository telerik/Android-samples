using System;
using Com.Telerik.Widget.Dataform.Visualization.Annotations;
using Com.Telerik.Widget.Dataform.Engine;
using Com.Telerik.Widget.Dataform.Visualization.Editors;

namespace Samples
{
	public class Person : NotifyPropertyChangedBase
	{
		private int age;
		private bool isEmployed;
		private EmployeeType employeeType;
		private string name;
		private string mail;
		private long birthDate;

		[DataFormProperty(Label = "Age", Index = 0)]
		public int getAge() {
			return age;
		}
		public void setAge(int value) {
			age = value;
			NotifyListeners("Age", value);
		}

		[DataFormProperty(Label = "Employee Type", Index = 1)]
		public EmployeeType getEmployeeType() {
			return employeeType;
		}
		public void setEmployeeType(EmployeeType value) {
			employeeType = value;
			NotifyListeners("EmployeeType", value);
		}

		[DataFormProperty(Label = "Name", Index = 0, ColumnIndex = 1)]
		public string getName() {
			return name;
		}
		public void setName(String value) {
			name = value;
			NotifyListeners("Name", value);
		}

		[DataFormProperty(Label = "E-mail", Index = 2)]
		public string getMail() {
			return mail;
		}
		public void setMail(String value) {
			this.mail = value;
			NotifyListeners("Mail", value);
		}

		[DataFormProperty(Label = "Employed", Index = 2, ColumnIndex = 1)]
		public bool getIsEmployed() {
			return isEmployed;
		}

		public void setIsEmployed(bool value) {
			isEmployed = value;
			NotifyListeners("IsEmployed", value);
		}

		//[DataFormProperty(Label = "Birth Date", Index = 1, ColumnIndex = 1, Editor = typeof(DataFormDateEditor))]
		public long getBirthDate() {
			return birthDate;
		}

		public void setBirthDate(long value) {
			this.birthDate = value;
			NotifyListeners("BirthDate", value);
		}

		public override String ToString() {
			//DateFormat dateFormat = SimpleDateFormat.getDateInstance();

			//return String.format("Name: %s, Age: %s, Mail: %s, EmployeeType: %s, IsEmployed: %s, BirthDate: %s",
				//name, Integer.toString(age), mail, employeeType.toString(), Boolean.toString(isEmployed), dateFormat.format(new Date(birthDate)));

			return String.Format("Name: %s, Age: %s, Mail: %s, EmployeeType: %s, IsEmployed: %s, BirthDate: %s",
				name, age.ToString(), mail, employeeType.ToString(), isEmployed.ToString());
		}
	}
}

