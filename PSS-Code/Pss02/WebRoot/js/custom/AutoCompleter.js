if (!dojo._hasResource["custom.AutoCompleter"]) {
	dojo._hasResource["custom.AutoCompleter"] = true;
	dojo.provide("custom.AutoCompleter");
	dojo.require("dijit.form.FilteringSelect");
	dojo.require("dojo.data.ItemFileReadStore");

	dojo.declare("custom.AutoCompleter", [ dijit.form.FilteringSelect ], {
		postCreate : function() {
			this.inherited(arguments);
			dojo.connect(this.textbox, "onkeyup", this, "onKeyup");
		},
		onKeyup : function(event) {
			var query = this._lastQuery;
			var newStore = new dojo.data.ItemFileReadStore({
				url : "test2.json?value=" + query,
				urlPreventCache : true,
				clearOnClose : true
			});
			this.attr("store", newStore);
		}
	});
}