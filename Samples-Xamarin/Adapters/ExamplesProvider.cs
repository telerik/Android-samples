using System;
using Java.Util;

namespace Samples
{
	public interface ExamplesProvider
	{
		String ControlName();
		LinkedHashMap Examples();
	}
}

