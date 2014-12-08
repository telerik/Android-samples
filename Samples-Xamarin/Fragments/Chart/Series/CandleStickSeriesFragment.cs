
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

using Android.App;
using Android.Content;
using Android.OS;
using Android.Runtime;
using Android.Views;
using Android.Widget;

namespace Samples
{

	public class CandleStickSeriesFragment : Fragment, ExampleFragment
	{
		public override void OnCreate (Bundle bundle)
		{
			base.OnCreate (bundle);

			// Create your application here
		}

		public String Title() {
			return "Candle stick series";
		}
	}
}

