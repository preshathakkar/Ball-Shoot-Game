
import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;

public class CanvasPanel extends JPanel implements Runnable
{
	private int xPos, yPos, stateCondition, ballCount;;
	private Vector ballThread;
	private CanvasPanel canvas;
	public int score;
	
	public boolean stopBallFlag, stopAllFlag, bounceMode;
	private Color currentBallColor;
	
	protected static final int ADD_BALL=1, DELETE_BALL=2, DELETE_ALL=3, STOP_BALL=4, STOP_ALL=5, SET_COLOR=6, SET_SIZE=7;
	protected static final int START = 8, STOP = 9, PAUSE = 10;
	public CanvasPanel()
	{
		addMouseListener(new MouseClickHandler());
		
		stopBallFlag		= false;
		stopAllFlag 		= false;

		this.setBackground(Color.WHITE);
		
		ballThread 			= new Vector();
		ballCount 			= 0;
		stateCondition 		= ADD_BALL;
		canvas 				= this;
		currentBallColor	= Color.BLACK;
		score = 0;
		
		//repaint();
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		Ball currentBall;
				
      	for(int i=0; i<ballThread.size(); i++)
      	{
      		currentBall = (Ball)ballThread.elementAt(i);
      		
      		if(currentBall != null)
      		{
      			if(bounceMode==true) this.checkBallCollision(currentBall, ballThread);
      			
      			currentBall.draw(g);
      		}
      	}      	
    }
	
	public void run()
   	{ 		
    	while(true)
    	{ 
			repaint();
			
         	try
      		{
            	Thread.sleep(50);
	     	}
	     	catch(Exception e){}
      	}	
   	}
   	
   	public void setStateCondition(int state)
   	{
   		stateCondition = state;
   	}
   	
   	public void setBallColor(Color color)
   	{
   		currentBallColor = color;
   	}
   	
	private class MouseClickHandler extends MouseAdapter
	{
		public synchronized void mouseClicked(MouseEvent event)
		{
			Ball currentBall;
			xPos = event.getX();
			yPos = event.getY();
			
			currentBall = getBallThreadByPosition(xPos,yPos,ballThread);
			
			if(currentBall != null)
			{					
				ballThread.setElementAt(null,Integer.parseInt(currentBall.getName()));
				
				currentBall.setRunState(false);
			
				repaint(); 
				score++;
			}
			
			
			
/*			switch(stateCondition)
			{
				case 1:   ADD_BALL 
				
					Ball ball = new Ball(xPos,yPos,canvas);
					System.out.println(ball);
					ball.setName(Integer.toString(ballCount++));		
					ballThread.add(ball);
					ball.start();
					break;
				
				case 2:  DELETE_BALL 
				
					currentBall = getBallThreadByPosition(xPos,yPos,ballThread);
					
					if(currentBall != null)
					{					
						ballThread.setElementAt(null,Integer.parseInt(currentBall.getName()));
						
						currentBall.setRunState(false);
					
						repaint();
					}
					break;
					
				case 3:  DELETE_ALL 
					
					for(int i=0; i<ballThread.size(); i++)
					{
						currentBall = (Ball)ballThread.elementAt(i);
						
						if(currentBall != null)
							currentBall.setRunState(false);;
					}
					ballThread.removeAllElements();
					ballCount = 0;
					break;						
							
				case 4:  STOP_BALL 
				
					currentBall = getBallThreadByPosition(xPos,yPos,ballThread);
					
					if(stopBallFlag == false)
						stopBall(currentBall);
					else
						startBall(currentBall);
					break;

				case 5:  STOP_ALL 
				
					if(stopAllFlag == false)
						stopAllBall(ballThread);
					else
						startAllBall(ballThread);
					break;
			
				case 6:  SET_COLOR 
					
					currentBall = getBallThreadByPosition(xPos,yPos,ballThread);
					
					if(currentBall != null)
					{
						currentBall.setBallColor(currentBallColor);
					}
					break;
					
					
				
				case 7:  SET_SIZE 
					
					currentBall = getBallThreadByPosition(xPos,yPos,ballThread);
					
					int ballRadius;
					
					if(currentBall != null)
					{
						try
						{
							ballRadius = Integer.parseInt(JOptionPane.showInputDialog(null,"Enter Ball Radius"));
						
							if(ballRadius > 0)
								currentBall.setBallRadius(ballRadius);
						}
						catch(Exception e){}
					}
					break;
			}		*/
		}
	}
	
	private Ball getBallThreadByPosition(int xPos, int yPos, Vector ballThread)
	{
		Ball currentBall;
				
		for(int i=0; i<ballThread.size(); i++)
		{
			currentBall = (Ball)ballThread.elementAt(i);
			
			if(currentBall != null)
			{	
				if( ((xPos >= currentBall.getXPos()) && (xPos <= (currentBall.getXPos()+currentBall.getRadius())))  &&  ((yPos >= currentBall.getYPos()) && (yPos <= (currentBall.getYPos()+currentBall.getRadius()))) )
				{
					return currentBall;
				}
			}
		}
		return null;
	}
	
	private void checkBallCollision(Ball inputBall, Vector ballThread) //Check Balls for Collision among each other
	{
		Ball currentBall;
		boolean x1=false,y1=false,x2=false,y2=false;
		
		for(int i=0; i<ballThread.size(); i++)
		{
			currentBall = (Ball)ballThread.elementAt(i);
			
			if(currentBall == null) continue;
			
			if((((inputBall.getXPos()+inputBall.getXDirection() > currentBall.getXPos()+currentBall.getXDirection()) && (inputBall.getXPos()+inputBall.getXDirection() < currentBall.getXPos()+currentBall.getXDirection()+currentBall.getRadius())) || ((currentBall.getXPos()+currentBall.getXDirection() > inputBall.getXPos()+inputBall.getXDirection()) && (currentBall.getXPos()+currentBall.getXDirection() < inputBall.getXPos()+inputBall.getXDirection()+inputBall.getRadius())))  &&  (((inputBall.getYPos()+inputBall.getYDirection() > currentBall.getYPos()+currentBall.getYDirection()) && (inputBall.getYPos()+inputBall.getYDirection() < currentBall.getYPos()+currentBall.getYDirection()+currentBall.getRadius())) || ((currentBall.getYPos()+currentBall.getYDirection() > inputBall.getYPos()+inputBall.getYDirection()) && (currentBall.getYPos()+currentBall.getYDirection() < inputBall.getYPos()+inputBall.getYDirection()+inputBall.getRadius()))))
			{	
				if(inputBall.getXDirection() < 0) x1=false; // x1 = negative
				if(inputBall.getXDirection() > 0) x1=true;  // x1 = positive
				if(inputBall.getYDirection() < 0) y1=false; // y1 = negative
				if(inputBall.getXDirection() > 0) y1=true;  // y1 = positive
				
				if(currentBall.getXDirection() < 0)	x2=false; // x2 = negative
				if(currentBall.getXDirection() > 0)	x2=true;  // x2 = positive
				if(currentBall.getXDirection() < 0)	y2=false; // y2 = negative
				if(currentBall.getXDirection() > 0)	y2=true;  // y2 = positive
				
				
				if((x1 == x2) && (y1 == y2)) // o->  o->  (2 Balls move in the same Direction)
				{
					if(inputBall.getXPos() <= currentBall.getXPos()) 
					{
						inputBall.inverseDirection();   // inputBall is in the left side 
					}
					else
					{
						currentBall.inverseDirection(); // currentBall is in the left side
					}
				}
				else // o->  <-o  (2 Balls move in opposite Direction)
				{
					if(inputBall.getXPos() <= currentBall.getXPos()) 
					{
						currentBall.setXPos(inputBall.getXPos()+inputBall.getRadius() + 1);
						
						currentBall.inverseDirection();
						inputBall.inverseDirection();
					}
					else
					{
						inputBall.setXPos(currentBall.getXPos()+currentBall.getRadius() + 1);						
						
						currentBall.inverseDirection();
						inputBall.inverseDirection();
					}
				}	
				break;
			}			
		}
	}
	
	private void stopBall(Ball currentBall)
	{
		if(currentBall != null)
			currentBall.setSuspendedState(true);
		
		stopBallFlag = true;
	}
	
	private void startBall(Ball currentBall)
	{
		if(currentBall != null)
		{
			currentBall.setSuspendedState(false);
			currentBall.notifyMe();
		}				
		stopBallFlag = false;
	}

	private void stopAllBall(Vector ballThread)
	{
		Ball currentBall;
		
		for(int i=0; i<ballThread.size(); i++)
		{
			currentBall = (Ball)ballThread.elementAt(i);
			
			if(currentBall != null)
			{
				currentBall.setSuspendedState(true);
			}
		}
		stopAllFlag = true;
	}
	
	public void stopAllBall()
	{
		stopAllBall(this.ballThread);
	}
	
	
	
	
	
	private void startAllBall(Vector ballThread)
	{
		Ball currentBall;
			
		for(int i=0; i<ballThread.size(); i++)
		{
			currentBall = (Ball)ballThread.elementAt(i);
				
			if(currentBall !=null)
			{
				currentBall.setSuspendedState(false);
				currentBall.notifyMe();
			}
		}
		stopAllFlag = false;
	}
	
	public void startAllBall()
	{
		startAllBall(ballThread);
	}
	
	public void ProduceBall(){
		for (int i = 0; i<5; i++){
			xPos = xPos + (int)(Math.random()*100);
			yPos = yPos + (int)(Math.random()*100);
			Ball ball = new Ball(xPos,yPos,canvas);
			System.out.println(ball);
			ball.setName(Integer.toString(ballCount++));		
			ballThread.add(ball);
			ball.start();
			}
			
		
	}
	
	public Vector getBallThread(){
		return this.ballThread;
	}
	
	public void DeleteAllBall(){
		for(int i=0; i<ballThread.size(); i++)
		{
			Ball currentBall;
			currentBall = (Ball)ballThread.elementAt(i);
			
			if(currentBall != null)
				currentBall.setRunState(false);;
		}
		ballThread.removeAllElements();
		ballCount = 0;
		
	}
	
	public void setBounceMode(boolean mode)
	{
		bounceMode = mode;
	}
}
