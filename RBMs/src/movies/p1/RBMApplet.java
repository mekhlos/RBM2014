package movies.p1;

import java.applet.Applet;
import java.awt.Button;
import java.awt.Checkbox;
import java.awt.CheckboxGroup;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.GroupLayout;

public class RBMApplet extends Applet implements ActionListener, ItemListener {
	
	int numVisible = 6;
	int numHidden = 2;
	int epoch = 50000;
	int numTrainingSets = 6;
	double learningRate = 0.01;
	double[][] weights;
	boolean afterTraining = false;
	double[][] trainingData =
			// "Harry Potter", "Avatar","LOTR 3", "Gladiator", "Titanic", "Glitter"
			{{1, 1, 1, 0, 0, 0}, 
			{1, 0, 1, 0, 0, 0},
			{1, 1, 1, 0, 0, 0}, 
			{0, 0, 1, 1, 1, 0}, 
			{0, 0, 1, 1, 0, 0}, 
			{0, 0, 1, 1, 1, 0}};

	Button startButton;  
    
    Container placeForResult;
    
    TextField rateField; 
    TextField visibleField;
    TextField hiddenField; 
    TextField trainingField;  
    
    CheckboxGroup radioGroup; 
    Checkbox expertMode; 
    Checkbox nonExpert;
    StringBuffer strBuffer;
	
    
    public void init() { 
    	
    	//setLayout(new FlowLayout()); 
        	
        startButton = new Button("Start the training!");         
        
        visibleField = new TextField("no. of visible units",20);
        hiddenField = new TextField("no. of hidden units",20);
        trainingField = new TextField("no. of training sets",20);
        rateField = new TextField("set learning rate",20);
        rateField.setEnabled(false);
        
        radioGroup = new CheckboxGroup(); 
        expertMode = new Checkbox("Expert Mode", radioGroup,false); 
        nonExpert = new Checkbox("Naiv Mode", radioGroup,true); 
        
        strBuffer = new StringBuffer();
        placeForResult = new Container();
        placeForResult.setLocation(200, 200);
        placeForResult.setBackground(Color.blue);
        placeForResult.add(startButton);
        //this.setBackground(Color.blue);
        //this.setMaximumSize();
        
        
        add(startButton);  
        add(rateField); 
        add(visibleField); 
        add(hiddenField); 
        add(trainingField); 
        add(placeForResult);
        add(expertMode); 
        add(nonExpert); 
  
        
        // Attach actions to the components 
        expertMode.addItemListener(this);
        nonExpert.addItemListener(this);
        startButton.addActionListener(this); 

        
        /*Container panel = new Container();
		GroupLayout layout = new GroupLayout(panel);
    	panel.setLayout(layout);
    	layout.setAutoCreateGaps(true);
    	layout.setAutoCreateContainerGaps(true);
    	
    	layout.setHorizontalGroup(
    			   layout.createSequentialGroup()
    			      .addComponent(rateField)
    			      .addComponent(visibleField)
    			      .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
    			           .addComponent(hiddenField)
    			           .addComponent(trainingField))
    			);
    			/*layout.setVerticalGroup(
    			   layout.createSequentialGroup()
    			      .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
    			           .addComponent(c1)
    			           .addComponent(c2)
    			           .addComponent(c3))
    			      .addComponent(c4)
    			);*/
    	
    }     

    @Override
    public void itemStateChanged(ItemEvent e) 
    {
    	if (e.getSource() == expertMode) {
    		rateField.setEnabled(true);
    	} else {
    		rateField.setEnabled(false);
    	}
    }
    
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == startButton)  {
			numVisible = Integer.parseInt(visibleField.getText());
			numHidden = Integer.parseInt(hiddenField.getText());
			
			
			RBM rbm = new RBM(numVisible, numHidden, learningRate);
			rbm.train(trainingData, epoch);
			strBuffer.append(rbm.getResult());
			weights = rbm.getResult();
			afterTraining = true;
			repaint();
			
		}/* else if (e.getSource() == exitButton) {
			this.destroy();
		}*/ 
		
	}
	
    public void stop() { 
    	this.destroy();
    } 

    @ Override
    public void paint(Graphics g) { 
    	g.drawRect(0, 0, 
    			   getWidth() - 2,
    			   getHeight() - 2);

    	
    	if (afterTraining) {
    	
	    	String[] movies = {"Bias", "Harry Potter", "Avatar",
					"LOTR 3", "Gladiator", "Titanic", "Glitter"};
	    	
			for (int i = 0; i < weights.length; i++) {
				g.drawString((movies[i] + "\t\t"), 10, 200 + i * 20);
				for (int j = 0; j < weights[i].length; j++) {
					g.drawString((weights[i][j] + "\t"), 200 + j * 200, 200 + i * 20);
				}
			}
    	}
    	
    	
	
    }
    
}
