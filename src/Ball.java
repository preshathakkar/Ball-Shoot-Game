
import java.awt.*;
import javax.swing.*;

public class Ball extends Thread 
{
	private int 	xDirection, yDirection, nowX, nowY, panelWidth, panelHeight, ballRate, ballRadius;
	private double 	ballRadians;
	private Color 	ballColor;
	
	private boolean runState, threadSuspended;
		
	public Ball(int xCoordinate,int yCoordinate, CanvasPanel canvas)
	{
        nowX		= xCoordinate;
        nowY		= yCoordinate;
        
        panelWidth	= canvas.getWidth();
        panelHeight = canvas.getHeight();
        
        ballRadians	= 2*Math.random()*Math.PI;
        ballRate	= (int)(3*Math.random()+1);
        ballRadius  = (int)(5*Math.random()+15);
        ballColor	= new Color((int)(255*Math.random()),(int)(255*Math.random()),(int)(255*Math.random()));
        
        xDirection	= (int)(ballRate*ballRadius*Math.cos(ballRadians) * 0.25);
        yDirection	= (int)(ballRate*ballRadius*Math.sin(ballRadians) * 0.25);       
    	
    	threadSuspended = false;
    	runState 		= true;
    }
	
	public void run()
	{
		while(runState)
    	{
    		try{
    			synchronized(this)
    			{
    				while(threadSuspended)
        				wait();      			
        		
        			this.step();
      				this.sleep(50);
       			}
      		}catch(Exception e){}
      	}
	}
	
	private synchronized void step()
   	{
        if(nowX <= 0)
        {
           xDirection	=	-xDirection;
        }
        else if((nowX+ballRadius) >= panelWidth)
        { 
           nowX			=	(panelWidth - ballRadius);
           xDirection	=	-xDirection;
        }

        nowX += xDirection;
        
        if(nowY <= 0)
        {
           yDirection	=	-yDirection;
        }
        else if((nowY+ballRadius) >= panelHeight)
        { 
           nowY			=	(panelHeight - ballRadius);
           yDirection	=	-yDirection;
        }

        nowY += yDirection;
   	}
	
	public void draw(Graphics g)
	{	
		g.setColor(ballColor);
		g.fillOval(nowX,nowY,ballRadius,ballRadius);
	}
	
	public void inverseDirection()
	{
		this.xDirection = -xDirection;
		this.yDirection = -yDirection;
	}
	
	public int getXPos()
   	{
   		return nowX;
   	}
   	public int getYPos()
   	{
   		return nowY;
   	}
   	public void setXPos(int xValue)
   	{
   		nowX = xValue;
   	}
   	public void setYPos(int yValue)
   	{
   		nowY = yValue;
   	}
   	
   	public int getXDirection()
   	{
   		return xDirection;
   	}
   	public int getYDirection()
   	{
   		return yDirection;
   	}
   	
   	public int getRadius()
   	{
   		return ballRadius;
   	}
	
	public void setBallRadians(double inRadians)
   	{
   		ballRadians = inRadians;
   	}
   	public void setBallRate(int inRate)
   	{
   		ballRate = inRate;
   	}
   	public void setBallRadius(int inRadius)
   	{
   		ballRadius = inRadius;	
   	}
   	public void setBallColor(Color inColor)
   	{
   		ballColor = inColor;
   	}

	public void setRunState(boolean state)
	{
		runState = state;
	}
   	public void setSuspendedState(boolean state)
   	{
   		threadSuspended = state;
   	}  
   	public synchronized void notifyMe()
   	{
   		this.notify();
   	}
   	public String toString(){
   		String S =  "X : "+nowX+" Y : "+nowY+" Xdir : "+xDirection+" Y : "+yDirection+"\n";
   		String T = "BallRate : "+ballRate+" BallRadians : "+ballRadians+" BallRadius"+ballRadius;
   		//U = S + T;
   		return S + T ;
   	}
}
