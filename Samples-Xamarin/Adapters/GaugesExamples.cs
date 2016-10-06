using System;
using Java.Util;

namespace Samples
{
	public class GaugesExamples: Java.Lang.Object, ExamplesProvider
	{
		private LinkedHashMap gaugeExamples;
		public GaugesExamples()
		{
			this.gaugeExamples = this.GetGaugeExamples();
		}

		public String ControlName()
		{
			return "GaugeView Beta";
		}

		public LinkedHashMap Examples()
		{
			return this.gaugeExamples;
		}

		private LinkedHashMap GetGaugeExamples()
		{
			LinkedHashMap examples = new LinkedHashMap();
			ArrayList result = new ArrayList();

			result.Add(new GaugesGettingStartedFragment());
			result.Add(new GaugesScalesFragment());
			result.Add(new GaugesAnimationsFragment());
			result.Add(new GaugeCustomizationFragment());
			examples.Put("Init", result);

			return examples;
		}

	}
}
