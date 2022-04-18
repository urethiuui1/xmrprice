module checker{
	requires transitive javafx.graphics;
	requires transitive javafx.controls;
	requires javafx.fxml;
	requires javafx.base;
	requires json.simple;
	exports watch to javafx.graphics;
	opens watch;
}