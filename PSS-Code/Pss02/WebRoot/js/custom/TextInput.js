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
							// If need server validate
							if (this.remote != "") {
								dojo.connect(this.textbox, "onkeyup", this,
										"remoteValidate");
							}
						},
						remoteValidate : function(event) {
							// If value is valid
							if (this.validate()) {
								this.serverValidate(this.domNode.name,
										this.domNode.value);
							}
						},
						serverValidate : function(name, value) {
							dojo.xhrPost({
								url : this.remote,
								content : {
									name : value
								},
								handleAs : 'json',
								load : this.hanldeResponse()
							});
							dijit.setWaiState(this.focusNode, "invalid",
									"false");
						},
						hanldeResponse : function(response, ioArgs) {
							this.displayMessage("Error");
							dojo
									.addClass(this.domNode,
											"dijitTextBoxError dijitValidationTextBoxError dijitError");
						}
					});
}