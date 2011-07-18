if (!dojo._hasResource["custom.AdvancedForm"]) {
	dojo._hasResource["custom.AdvancedForm"] = true;
	dojo.provide("custom.TextInput");
	dojo.require("dijit.form.Form");

	dojo.declare("dijit.form.Form", [ dijit.form.ValidationTextBox ], {
		postCreate : function() {
			this.inherited(arguments);

		}
	});
}