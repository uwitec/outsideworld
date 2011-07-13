if (!dojo._hasResource["custom.TextInput"]) {
	dojo._hasResource["custom.TextInput"] = true;
	dojo.provide("custom.TextInput");
	dojo.require("dijit.form.ValidationTextBox");
	dojo.require("dijit._Templated");

	dojo
			.declare(
					"custom.TextInput",
					[ dijit.form.ValidationTextBox ],
					{
						remote : "",
						constructor : function(params, node) {
							this.remote = node.getAttribute("remote");
						},
						postCreate : function() {
							this.inherited(arguments);
							// if need server validate
							if (this.remote != "") {
								dojo.connect(this.textbox, "onblur", this,
										"remoteValidate");
							}
						},
						remoteValidate : function(event) {
							// if value is valid
							if (this.validate()) {
								this.serverValidate(this.name, this
										.get("value"));
							}
						},
						serverValidate : function(name, value) {
							// form data
							var content = new Object();
							content[name] = value;

							dojo.xhrPost({
								self : this,
								url : this.remote,
								content : content,
								handleAs : 'json',
								load : this.hanldeResponse
							});
						},
						hanldeResponse : function(response, ioArgs) {
							if (response.valid != "true") {
								this.self.displayMessage("Error");
								dojo
										.addClass(this.self.domNode,
												"dijitTextBoxError dijitValidationTextBoxError dijitError");
							} else {
								this.self.displayMessage("");
								dojo
										.removeClass(this.self.domNode,
												"dijitTextBoxError dijitValidationTextBoxError dijitError");
							}
						}
					});
}