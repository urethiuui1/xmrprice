package watch;



import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.util.ResourceBundle;

import org.json.simple.parser.ParseException;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.util.Duration;


public class Controller implements Initializable{
	@FXML private Label lprice;
	@FXML private Label lchange;
	Price p = new Price("monero");
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			updatePrice();
		} catch (IOException | ParseException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
		
		Timeline timeline = new Timeline(
			    new KeyFrame(Duration.seconds(60.0), e -> {
			    	try {
						updatePrice();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (ParseException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
			    })
			);
			timeline.setCycleCount(Timeline.INDEFINITE);
			timeline.play();
	}
	public void updatePrice() throws IOException, ParseException {
		p.getData();
		lprice.setText(p.getPrice() + " USD");
		updateColor();
	}
	public void updateColor() throws IOException, ParseException {
		Double performance = p.getChange();
		if (performance > 0) {
			lprice.setTextFill(Color.GREEN);
			lchange.setTextFill(Color.GREEN);
			lchange.setText(performance.toString() + "%↑");
		}else {
			lprice.setTextFill(Color.RED);
			lchange.setTextFill(Color.RED);
			lchange.setText(performance.toString() + "%↓");
		}
	}
	
}