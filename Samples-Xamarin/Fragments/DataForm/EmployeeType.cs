using System;

namespace Samples
{
	public class EmployeeType : Java.Lang.Object
	{
		public static readonly int PROGRAMMER = 0;
		public static readonly int MANAGER = 1;
		public static readonly int SUPPORT = 2;
		public static readonly int MARKETING = 3;

		public static string[] StringValues() 
		{
			return new string[] { "PROGRAMMER", "MANAGER", "SUPPORT", "MARKETING" };
		}

		public static int[] Values() 
		{
			return new int[] { PROGRAMMER, MANAGER, SUPPORT, MARKETING };
		}
	}
}

