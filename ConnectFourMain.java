import java.awt.AWTException;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Robot;
import java.awt.event.*;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

public class ConnectFourMain {
	static String player = "";
	public ConnectFourMain() {	
		
		JFrame gameFrame = new JFrame(); // window
		gameFrame.setTitle("CONNECT4 GAME");
		gameFrame.pack();
		gameFrame.setSize(1000, 800);
		gameFrame.setPreferredSize(gameFrame.getSize());
		gameFrame.add(new ConnectFourGame(gameFrame.getSize()));
		gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);     
		gameFrame.setVisible(true);
        
	}
	
	public static void main(String args[]) {
		
		JFrame frame = new JFrame();
		frame.setTitle("CONNECT4 GAME");
        
        JPanel panel = new JPanel(new GridBagLayout());
        JLabel headline = new JLabel("CONNECT4");
        headline.setFont(headline.getFont().deriveFont(Font.BOLD, 48f));
        
		JLabel label = new JLabel("Play against ?");
		Font font = new Font("Courier New", 1, 20);
		Dimension size = label.getPreferredSize();
        label.setBounds(150, 150, size.width, size.height);
		label.setFont(font);
		
		JButton button1 = new JButton("Computer");
		button1.setPreferredSize(new Dimension(200, 40));
		button1.setFont(font);
		JButton button2 = new JButton("Human");
		button2.setPreferredSize(new Dimension(200, 40));
		button2.setFont(font);
		
		GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        
		panel.add(headline,gbc);
		panel.add(label,gbc);
		panel.add(button1,gbc);
		panel.add(button2,gbc);
		panel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 70, false));
		
		frame.add(panel);
		frame.pack();
		frame.setSize(1000, 800);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);     
        frame.setVisible(true);
		button1.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				frame.dispose();
				player = "computer";
				ConnectFourMain connectFourMain = new ConnectFourMain();				
			}
		});
		button2.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				frame.dispose();
				player = "human";
				ConnectFourMain connectFourMain = new ConnectFourMain();
			}
		});
		//ConnectFourMain connectFourMain = new ConnectFourMain();
	}
	
	static class ConnectFourGame extends JPanel implements MouseListener, ActionListener { //JPanel extended::
		int row = 6, col = 7, blueX = 50, blueY = 20, 
			discSize = 65, boardwidth = 600, boardHeight = 600, 
			initX = 60, initY = 100, whiteX = 700, horizontalGap = 20, verticalGap = 100, turn = 2;		
		boolean endGame = false, triggerOnce = false;
		Color[][] board = new Color[row][col]; // can be integer array too
		
		private Polygon resetBox;
		
		ConnectFourGame(Dimension dimension) {
			
			resetBox = new Polygon();
			resetBox.addPoint(725, 200);
			resetBox.addPoint(925, 200);
			resetBox.addPoint(925, 275);
			resetBox.addPoint(725, 275);
			addMouseListener(this);		
            for (int i = 0; i < row; i++) 
                for (int j = 0; j < col; j++) 
                	board[i][j] = Color.white; 
            
		}
		@Override
		public void paintComponent(Graphics graphics) {	
			super.paintComponent(graphics);
			Graphics2D g = (Graphics2D)graphics;
			g.setStroke(new BasicStroke(5.0f));
			Dimension d = getSize();
			String c = "";
			Font font = new Font("Courier New", 1, 20);
			g.setFont(font);
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, d.width, d.height);
			
			g.setColor(new Color(0, 0, 255));
			g.fillRect(blueX, blueY, boardwidth, boardHeight);
			
			g.setColor(new Color(255, 255, 255));
			g.fillRect(whiteX, blueY, 250, boardHeight);
			int k = 1;
			initX = 60; 
			initY = 50;
			for(int i = 0; i < row; i++) {
				for(int j = 0; j < col; j++) {
					g.setColor(board[i][j]);
					g.fillOval(initX, initY, discSize, discSize);
					g.setColor(Color.BLACK);
					g.drawOval(initX, initY, discSize, discSize);
					//g.drawString(String.valueOf(i), initX, initY);
					initX += discSize + horizontalGap;
				}
				initX = 60;
				initY = verticalGap * k + 30;
				//initY += 10;
				k++;
			}
			
			if(!endGame) {
				g.setColor(new Color(0, 0, 0));
				if(turn % 2 == 0) {
	                //g.drawString("Player: Red", 765, 40);                
	                g.drawRect(705, 50, 240, 125);
	                g.setColor(Color.RED);
	                g.fillRect(705, 50, 240, 125);
	                g.setColor(new Color(0, 0, 0));	
	                g.drawString("Player: Red", 750, 115);  
	                if(player.equalsIgnoreCase("computer")){
	                	triggerOnce = true;
	                }
	                
				} else {
	               // g.drawString("Player: Yellow", 765, 40);
	                g.drawRect(705, 50, 240, 125);
	                g.setColor(Color.yellow);
	                g.fillRect(705, 50, 240, 125);
	                g.setColor(new Color(0, 0, 0));
	                g.drawString("Player: Yellow", 750, 115); 
	            }
			} else {
				if(turn % 2 == 0) {					
	                g.setColor(Color.yellow);
	                g.fillRect(700, blueY, 250, 600);
	                //g.drawString("Yellow wins", 775, 40);
	                c = "Yellow wins";
				} else {
	                g.setColor(Color.red);
	                g.fillRect(700, blueY, 250, 600);
	                //g.drawString("Red wins", 775, 40);
	                c = "Red wins";
				}
				//System.out.println(turn);
				g.setColor(new Color(0, 0, 0));				
				g.drawString(c, 770, 100);
				g.draw(resetBox);
				g.drawString("Start New Game", 745, 245);				
			}
			repaint();
		}
		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			int selectCol = -1;			
			int maximum = 5, minimum = 0;
			int x = e.getX(), y = e.getY();			
			if(x <= 295) 
				x -= 26;
			else if(x >= 485)
				x += 30;
			
			selectCol = x/100;
			
			if(player.equalsIgnoreCase("computer") && turn % 2 == 1) {
				Random rand = new Random();
				int randomNum = minimum + rand.nextInt((maximum - minimum) + 1);
				selectCol = randomNum;
				triggerOnce = false;
			}
			int selectRow = -1;
			//System.out.println("x " + x + " y " + y +" selectCol " + selectCol);
			if(!endGame) {
				if(selectCol < col) {
					int dropRow = row - 1;
					//System.out.println(dropRow+" "+selectCol);
					while(row >= 0) {
						if(board[dropRow][selectCol] == Color.WHITE) {
							selectRow = dropRow;
							break;
						}
						dropRow--;
					}
					//System.out.println("selectRow "+selectRow);
					if(selectRow != -1) {
						if(turn % 2==0){
							board[selectRow][selectCol]= Color.red;
						} else {
							board[selectRow][selectCol]= Color.yellow;
						}
						turn++;
						}
					endGame = searchBoard(board, selectRow, selectCol);
					//System.out.println("EndGame "+endGame);					
				}
			}			
			repaint();
			
		}
		public static boolean searchBoard(Color[][] board, int selectRow, int selectCol) {
			Color selectColor = board[selectRow][selectCol];
			int count = 1;
			//Horizontal search
			//left side
			int left = selectCol;
			left--;
			while(left >= 0 && board[selectRow][left] == selectColor) {
				count++;	
				if(count == 4) return true;
				left--;
			}
			//right side
			int right = selectCol;
			right++;
			while(right < board[0].length && board[selectRow][right] == selectColor) {
				count++;			
				if(count == 4) return true;
				right++;
			}
			
			//Vertical Search
			//down side
			count = 1;
			int down = selectRow;
			down++;
			while(down < board.length && board[down][selectCol] == selectColor) {
				count++;	 
				if(count == 4) return true;
				down++;
			}
			//top side
			int top = selectRow;
			top--;
			while(top >= 0 && board[top][selectCol] == selectColor) {
				count++; 
				if(count == 4) return true;
				top--;
			}
			
			//Diagonal search
			//top left to down right
			count = 1;
			left = selectCol;
			top = selectRow;
			top--; left--;
			while(left >= 0 && top >= 0 && board[top][left] == selectColor ) {
				count++; 
				if(count == 4) return true;
				top--; left--;				
			}
			
			right = selectCol;
			down = selectRow;
			right++; down++;
			while(right < board[0].length && down < board.length && board[down][right] == selectColor ) {
				count++; 
				if(count == 4) return true;
				down++;	right++;
			}
			
			//top right to down left
			count = 1;
			right = selectCol;
			top = selectRow;
			right++; top--;
			while(right < board[0].length && top >= 0 && board[top][right] == selectColor) {
				count++; 
				if(count == 4) return true;
				right++; top--;
			}
			
			down = selectRow;
			left = selectCol;
			down++; left--;
			while(left >= 0 && down < board.length && board[down][left] == selectColor ) {
				count++; 
				if(count == 4) return true;
				left--; down++;
			}
			return false;
			
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			if(endGame) {
				Point p = e.getPoint(); 
	            if(resetBox.contains(p)) {            	
	                endGame = false;
	                turn = 2;
	                for (int i = 0; i < row; i++) 
	                    for (int j = 0; j < col; j++) 
	                        board[i][j] = Color.white;
	            }
			}
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			if (player == "computer" && turn % 2 == 1 && triggerOnce) {
            	Robot bot;
				try {
					bot = new Robot();  
					triggerOnce= false;
				    bot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
				    bot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
					
				} catch (AWTException exception) {
					// TODO Auto-generated catch block
					exception.printStackTrace();
				}  
            }
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			//repaint();
		}
		
		
	}
	
	
	
	
}
 