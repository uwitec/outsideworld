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
						correct : "false",
						constructor : function(params, node) {
							this.remote = node.getAttribute("remote");
							// if not required supposed correct and can submit
							if (node.getAttribute("required") != "true") {
								this.correct = "true";
							}
						},
						postCreate : function() {
							this.inherited(arguments);
							// validate value
							dojo
									.connect(this.textbox, "onblur", this,
											"onBlur");
						},
						onBlur : function(event) {
							if (this.validate() && this.remote != null
									&& this.remote != "") {
								this.serverValidate(this.name, this
										.get("value"));
								this.correct = "unknow";
							} else if (this.validate()
									&& (this.remote == "" || this.remote == null)) {
								correct = "true";
							} else {
								correct = "false";
								this.focus();
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
							if (response[this.self.name + "Valid"] != true) {
								this.self.focus();
								this.self
										.displayMessage(response[this.self.name
												+ "Msg"]);
								dojo
										.addClass(this.self.domNode,
												"dijitTextBoxError dijitValidationTextBoxError dijitError");
								this.self.correct = "false";
							} else {
								this.self.displayMessage("");
								dojo
										.removeClass(this.self.domNode,
												"dijitTextBoxError dijitValidationTextBoxError dijitError");
								this.self.correct = "true";
							}
						}
					});
}