using System;
using Com.Telerik.Widget.Dataform.Engine;
using Com.Telerik.Widget.Dataform.Visualization.Editors;
using Java.Text;
using Java.Util;
using Com.Telerik.Widget.Dataform.Visualization.Annotations;


namespace Samples
{
	public class Person : NotifyPropertyChangedBase
	{
		private int age = 18;
		private bool isEmployed = false;
		private EmployeeType employeeType = EmployeeType.PROGRAMMER;
		private string name = "";
		private string mail = "";
		private long birthDate = 0L;

		[@DataFormProperty(Label = "Age", Index = 0, Validator = typeof(RangeValidator), Group = "Group 1")]
		public int Age 
		{
			get 
			{
				return age;
			}
			set 
			{
				age = value;
				NotifyListeners ("Age", value);
			}
		}

		[@DataFormProperty(Label = "Employee Type", Index = 1, Group = "Group 1")]
		public EmployeeType EmployeeType 
		{
			get 
			{
				return employeeType;
			}
			set 
			{
				employeeType = value;
				NotifyListeners ("EmployeeType", Java.Lang.String.ValueOf(value.ToString()));
			}
		}

		[@DataFormProperty(Label = "Name", Index = 0, ColumnIndex = 1, Group = "Group 2")]
		public string Name {
			get {
				return name;
			}
			set {
				name = value;
				NotifyListeners ("Name", value);
			}
		}

		[@DataFormProperty(Label = "E-mail", Index = 2, Validator = typeof(MailValidator), Group = "Group 2")]
		public string Mail {
			get{
				return mail;
			}
			set {
				this.mail = value;
				NotifyListeners ("Mail", value);
			}
		}

		[@DataFormProperty(Label = "Employed", Index = 2, ColumnIndex = 1, Group = "Group 3")]
		public bool IsEmployed {
			get {
				return isEmployed;
			}
			set {
				isEmployed = value;
				NotifyListeners ("IsEmployed", value);
			}
		}

		[@DataFormProperty(Label = "Birth Date", Index = 1, ColumnIndex = 1, Editor = typeof(DataFormDateEditor), Group = "Group 3")]
		public long BirthDate {
			get{
				return birthDate;
			}
			set {
				this.birthDate = value;
				NotifyListeners ("BirthDate", value);
			}
		}

		public override String ToString() {
			DateFormat dateFormat = SimpleDateFormat.DateInstance;

			return String.Format("Name: {0}, Age: {1}, Mail: {2}, EmployeeType: {3}, IsEmployed: {4}, BirthDate: {5}",
				name, age.ToString(), mail, employeeType.ToString(), isEmployed.ToString(), dateFormat.Format(new Date(birthDate)));
		}
	}
}

