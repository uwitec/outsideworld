if (!dojo._hasResource["custom.AutoCompleter"]) {
	dojo._hasResource["custom.AutoCompleter"] = true;
	dojo.provide("custom.AutoCompleter");
	dojo.require("dijit.form.FilteringSelect");
	dojo.require("dojo.data.ItemFileReadStore");

	dojo.declare("custom.AutoCompleter", [dijit.form.FilteringSelect], {
				postCreate : function() {
					this.inherited(arguments);
					dojo.connect(this.textbox, "onkeyup", this, "onKeyup");
					dojo.connect(this.textbox, "onblur", this, "onBlur");
				},
				onKeyup : function(event) {
					var temp = this.attr("store").url;
					if (temp.indexOf("?") > 0) {
						this.attr("store").url = temp.substring(0, temp
										.indexOf("?"))
					}
					var query = this.textbox.value.trim();
					if(query==''){
						return;
					}
					var newStore = new dojo.data.ItemFileReadStore({
								url : this.attr("store").url + "?value="
										+ query,
								urlPreventCache : true,
								clearOnClose : true
							});
					this.attr("store", newStore);					

				},
				onBlur:function(event) {					
					var obj = dojo.query('[acco]').at(0);
					obj.attr("value",this.textbox.value.trim());
				}
			});
}