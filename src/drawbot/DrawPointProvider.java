package drawbot;

import java.util.ArrayList;
import static java.lang.Math.*;

public class DrawPointProvider {
	private ArrayList<Pointt> points = new ArrayList<Pointt>();
	private int curIndex;
	
	public DrawPointProvider() {
		setupPoints();
	}
	
	private void setupPoints() {
//		spiral();
//		upAndDown();
//		sideSide();
		jags();
	}
	private void spiral() {
		double r = DrawBot.CanvasHeight/2.0;
		Pointt origin = new Pointt(DrawBot.CanvasWidth/2.0, DrawBot.CanvasHeight/2.0);
		int slices = 5;
		for(int i=0; i < 40; ++i) {
			double angle = (i/(double) slices) * PI * 2.0;
			Pointt off = new Pointt(r*cos(angle), r*sin(angle));
			points.add(origin.plus(off));
			r *= .95;
		}
	}
	private void upAndDown() {
		Pointt origin = new Pointt(DrawBot.CanvasWidth/2.0, DrawBot.CanvasHeight/2.0);
		for(int i=0; i < 40; ++i) {
			Pointt off = new Pointt(0,(i%2==0)? 0 :  200);
			points.add(origin.plus(off));
		}
	}
	private void sideSide() {
		Pointt origin = new Pointt(DrawBot.CanvasWidth/2.0, DrawBot.CanvasHeight/2.0);
		for(int i=0; i < 40; ++i) {
			Pointt off = new Pointt((i%2==0)?-200 :  200, 0);
			points.add(origin.plus(off));
		}
	}
	private void jags() {
		Pointt origin = new Pointt(DrawBot.CanvasWidth/2.0 * 0.0, DrawBot.CanvasHeight/2.0);
		for(int i=0; i < 40; ++i) {
			Pointt off = new Pointt((i/40.0) * DrawBot.CanvasWidth, (i%2==0)?-200 :  200);
			points.add(origin.plus(off));
		}
	}
	public Pointt nextPoint() {
		Pointt res = points.get(curIndex);
		curIndex = (curIndex + 1) % points.size();
		return res;
	}
}
