package br.unb.cic.lp.gol;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;


/**
 * Atua como um componente de apresentacao (view), exibindo o estado atual do
 * game com uma implementacao baseada em caracteres ASCII.
 * 
 * @author rbonifacio
 */


public class GameView {
	
	private GameEngine engine;
	private GameController controller;
	
	int i, j;
	JFrame gameWindow = new JFrame("Game of Life");
	JButton board[][] = new JButton [10][10];
	JPanel boardGrid = new JPanel(new GridLayout(10,10,5,6));
	
	
	/**
	 * Construtor da classe GameBoard
	 */
	public GameView(GameController controller, GameEngine engine) {
		this.controller = controller;
		this.engine = engine;
	}

	/**
	 * Atualiza o componente view (representado pela classe GameBoard),
	 * possivelmente como uma resposta a uma atualizacao do jogo.
	 */
	

	public void setup(){
		
		for (i = 0; i < engine.getHeight(); i++) {
			for (j = 0; j < engine.getWidth(); j++) {
				board[i][j] = new JButton(" \n ");
				boardGrid.add("", board[i][j]);
			}
		}
		update();
	}
	
	
/*Atualiza o programa todas as rodadas*/
	public void update() {
		
		gameWindow.setVisible(false);
		for (i = 0; i < engine.getHeight(); i++) {
			for (j = 0; j < engine.getWidth(); j++) {
				board[i][j].setEnabled(false);
				if (engine.isCellAlive(i, j))
					board[i][j].setBackground(Color.black);
				else 
					board[i][j].setBackground(Color.white);
			}
		}
		
		gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gameWindow.setSize(500, 500);
		gameWindow.add(boardGrid, "North");
		gameWindow.setResizable(false);
		gameWindow.setVisible(true);
		
		printOptions();
	}

/* Imprime as opções do jogo*/
	private void printOptions() {
		
		JButton makeAlive = new JButton("Make a Cell Alive");
		JButton nextGen = new JButton("Next Generation");
		JButton halt = new JButton("Halt");
		
		final JPanel painel = new JPanel();
		painel.add(makeAlive);
		painel.add(nextGen);
		painel.add(halt);
		
		gameWindow.getContentPane().add(painel, "South");
		gameWindow.pack();
				
		ActionListener makeAliveAction = new ActionListener() {
			  public void actionPerformed(ActionEvent e) {
				  makeCellAlive();
			  }
		};
		
		ActionListener nextGenAction = new ActionListener() {
			  public void actionPerformed(ActionEvent e) {
				 nextGeneration();
			  }
		};
		
		ActionListener haltAction = new ActionListener() {
			  public void actionPerformed(ActionEvent e) {
				 halt();
			  }
		};
			
		makeAlive.addActionListener(makeAliveAction);
		halt.addActionListener(haltAction);
		nextGen.addActionListener(nextGenAction);
	}
	
	
	private void makeCellAlive() {
		
		//Para escolher uma célula viva com um click (possui mensagem pop-up)
		Component frame = null;
		JOptionPane.showMessageDialog(frame,
			    "Click on the cell you wish to make alive.\n",
			    "Mission: Make Alive",
			    JOptionPane.PLAIN_MESSAGE);
		
		ActionListener chosenCell = new ActionListener() {
			  public void actionPerformed(ActionEvent e) {
				 Object source = e.getSource();
				 
				 for (i = 0; i < engine.getHeight(); i++) {
						for (j = 0; j < engine.getWidth()-1; j++) {
							if (source == board[i][j]){
								break;
							}
						}
					if (source == board[i][j]){
						break;
				 	}
					
				 }
				 
				 if (validPosition(i,j)) {
						controller.makeCellAlive(i, j);
					}
					else {
						System.out.println("Coordenadas Inválidas");
						update();
					}
			  }
		};
		
		
		for (int i2 = 0; i2 < engine.getHeight(); i2++) {
			for (int j2 = 0; j2 < engine.getWidth(); j2++) {
				board[i2][j2].setEnabled(true);
				board[i2][j2].addActionListener(chosenCell);
			}
		}
		
		/*
		
		//Para escolher uma nova célula viva pelas coordenadas
		final JTextField coordX = new JTextField(50);
		JPanel xCoordinates = new JPanel();
		xCoordinates.add(coordX);
		final JFrame xWindow = new JFrame("Inform the row number (0 - " + engine.getHeight() + "): ");
		xWindow.add(xCoordinates);
		xWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE );
		xWindow.pack();
		xWindow.setVisible(true);

		
		ActionListener readX = new ActionListener() {
			  public void actionPerformed(ActionEvent e) {
				xWindow.dispose();
				i = Integer.parseInt(coordX.getText());
				
				final JTextField coordY = new JTextField(50);
				JPanel yCoordinates = new JPanel();
				yCoordinates.add(coordY);
				final JFrame yWindow = new JFrame("Inform the column number (0 - " + engine.getWidth() + "): ");
				yWindow.add(yCoordinates);
				yWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE );
				yWindow.pack();
				yWindow.setVisible(true);
				
				ActionListener readY = new ActionListener() {
					  public void actionPerformed(ActionEvent e) {
						yWindow.dispose();
						j = Integer.parseInt(coordY.getText());
						if (validPosition(i,j)) {
							controller.makeCellAlive(i, j);
						}
						else {
							Component frame = null;
							JOptionPane.showMessageDialog(frame, "Coordenadas Inválidas");
							update();
						}
					 }
				};
				
				coordY.addActionListener(readY);
			  }
		};
		coordX.addActionListener(readX);
		*/
	}
	
	
	private void nextGeneration() {
		controller.nextGeneration();
	}
	
	private void halt() {
		gameWindow.setVisible(false);
		controller.halt();
	}
	
	private boolean validPosition(int i, int j) {
		return i >= 0 && i < engine.getHeight() && j >= 0 && j < engine.getWidth();
	}

}