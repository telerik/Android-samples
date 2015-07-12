using System;
using Com.Telerik.Widget.Dataform.Engine;

namespace Samples
{
	public class NonEmptyValidator : Java.Lang.Object, IPropertyValidator {
		public void Validate(Java.Lang.Object o, IValidationCompletedCallback validationCompletedCallback) {

			ValidationInfo info;

			if(o == null || o.ToString().Equals("")) {
				info = new ValidationInfo(false, "This field can not be empty.", o);
			} else {
				info = new ValidationInfo(true, "The entered value is valid.", o);
			}

			validationCompletedCallback.ValidationCompleted(info);
		}
	}
}

