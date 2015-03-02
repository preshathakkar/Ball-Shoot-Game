import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class ToolbarPanel extends JToolBar
{
	private JRadioButton addBall, deleteBall, deleteAllBall, stopBall, stopAllBall, setColor, setSize;
	private JCheckBox  bounceModeCheckBox;
	private JButton start,stop,pause;
	
	
	private CanvasPanel canvasPanel;
	private InfoPanel infoPanel;
	
	private ButtonGroup radioGroup;
	private Color ballColor;
	
	public ToolbarPanel(CanvasPanel cPanel, InfoPanel iPanel)
	{
		canvasPanel 	 = cPanel;
		infoPanel		 = iPanel;
		
		bounceModeCheckBox = new JCheckBox("Bounce Mode");
		bounceModeCheckBox.addItemListener(
			new ItemListener()
			{
				public void itemStateChanged(ItemEvent event)
				{
					if(bounceModeCheckBox.isSelected())
						canvasPanel.setBounceMode(true);
					else
						canvasPanel.setBounceMode(false);
					
					canvasPanel.repaint();
				}
			}
		);
		
		start = new JButton("Start");
		stop = new JButton("Stop");
		pause = new JButton("Pause");
		
		start.addActionListener(new StartButtonHandler());
		stop.addActionListener(new StopButtonHandler());
		pause.addActionListener(new PauseButtonHandler());
		
		radioGroup 		 = new ButtonGroup();
		
		addBall			 = new JRadioButton("Add Ball",true);	//default
		addBall.addItemListener(new RadioButtonHandler());
		
		deleteBall 		 = new JRadioButton("Delete Ball");
		deleteBall.addItemListener(new RadioButtonHandler());
		
		deleteAllBall	 = new JRadioButton("Delete All");
		deleteAllBall.addItemListener(new RadioButtonHandler());;
		
		stopBall		 = new JRadioButton("Stop/Start Ball");
		stopBall.addItemListener(new RadioButtonHandler());
		
		stopAllBall		 = new JRadioButton("Stop/Start All");
		stopAllBall.addItemListener(new RadioButtonHandler());
		
		setColor		 = new JRadioButton("Set Color");
		setColor.addItemListener(new RadioButtonHandler());
		
		setSize			 = new JRadioButton("Set Size");
		setSize.addItemListener(new RadioButtonHandler());
		
		radioGroup.add(addBall);
		radioGroup.add(deleteBall);
		radioGroup.add(deleteAllBall);
		radioGroup.add(stopBall);
		radioGroup.add(stopAllBall);
		radioGroup.add(setColor);
		radioGroup.add(setSize);
		
		this.setLayout(new GridLayout(1,3,2,2));
		this.setBackground(Color.BLACK);

		/*this.add(addBall);
		this.add(deleteBall);
		this.add(deleteAllBall);
		this.add(stopBall);
		this.add(stopAllBall);
		this.add(setColor);
		this.add(setSize);
		this.add(bounceModeCheckBox);*/
		this.add(start);
		this.add(stop);
		this.add(pause);
	}
	
	private class RadioButtonHandler implements ItemListener
	{
		public void itemStateChanged(ItemEvent event)
		{
			if(addBall.isSelected())
			{	
				canvasPanel.setStateCondition(canvasPanel.ADD_BALL);
				infoPanel.setText("Please Click on the Canvas to ADD Ball");
			}
			if(deleteBall.isSelected())
			{
				canvasPanel.setStateCondition(canvasPanel.DELETE_BALL);	
				infoPanel.setText("Please Click on a Ball to do Deletion");
			}
			if(deleteAllBall.isSelected())
			{
				canvasPanel.setStateCondition(canvasPanel.DELETE_ALL);
				infoPanel.setText("Please Click on the Canvas to DELETE ALL Balls");
			}
			if(stopBall.isSelected())
			{
				canvasPanel.setStateCondition(canvasPanel.STOP_BALL);
				infoPanel.setText("Please Click on a Ball to PAUSE/RESUME the ball's thread");
			}
			if(stopAllBall.isSelected())
			{
				canvasPanel.setStateCondition(canvasPanel.STOP_ALL);
				infoPanel.setText("Please Click on the Canvas to PAUSE/RESUME ALL Balls");
			}
			if(setColor.isSelected())
			{
				ballColor = JColorChooser.showDialog(ToolbarPanel.this,"Choose a color",ballColor);
				
				if(ballColor != null)
					canvasPanel.setBallColor(ballColor);
				
				canvasPanel.setStateCondition(canvasPanel.SET_COLOR);
				infoPanel.setText("Please Click on a Ball to Change its COLOR");
			}
			if(setSize.isSelected())
			{
				canvasPanel.setStateCondition(canvasPanel.SET_SIZE);
				infoPanel.setText("Please Click on a Ball to Change its SIZE");
			}
		}
	}

	private class StartButtonHandler implements ActionListener{

	@Override
	public void actionPerformed(ActionEvent arg0) {
		//System.out.println("Am Start");
		//System.out.println("StopAllFlag : "+canvasPanel.stopAllFlag+" BallThread : "+canvasPanel.getBallThread());
		if(canvasPanel.stopAllFlag == false)
			{canvasPanel.ProduceBall();
			infoPanel.setText("Pause : Pause game ||  Stop : End game ");
			
			}
		else if(canvasPanel.stopAllFlag == true && canvasPanel.getBallThread().isEmpty()){
			canvasPanel.ProduceBall();
			canvasPanel.stopAllFlag = false;
		}
		else
			canvasPanel.startAllBall();
		
		System.out.println("StopAllFlag : "+canvasPanel.stopAllFlag+" BallThread : "+canvasPanel.getBallThread());
		//canvasPanel.startAllBall();
		//canvasPanel.startAllBall();
		// TODO Auto-generated method stub
		
	}
	}
	
	private class StopButtonHandler implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			System.out.println("Am Stop");
			canvasPanel.DeleteAllBall();
			infoPanel.setText("Your Final Score is : "+canvasPanel.score);
			canvasPanel.score = 0;
			//canvasPanel.startAllBall();
			// TODO Auto-generated method stub
			
		}
		}
	
	private class PauseButtonHandler implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			System.out.println("Am pause");
			if(canvasPanel.stopAllFlag == false)
				canvasPanel.stopAllBall();
			else
				canvasPanel.startAllBall();
		
			//canvasPanel.startAllBall();
			// TODO Auto-generated method stub
			
		}
		}
	
	

}
