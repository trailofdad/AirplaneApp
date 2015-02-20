import java.util.Arrays;

import javax.swing.JList;
import javax.swing.JOptionPane;

public class Application {
	
	//initialize 2D boolean array
	boolean[][] seats = new boolean[4][4];
	//string array to hold the passengers and assigned seats ****Initialize so the Arrays.sort works*****
	String[] passengersAndSeats = {"","","","","","","",""};	
	//a rolling count of the number of passengers 
	int numOfPassengers = 0;
	
	//start method
	public void start() {
		if(numOfPassengers == 16)
		{
			JOptionPane.showMessageDialog(null, "The plane is now full. The next flight leaves in 3 hours.");
			System.exit(0);
		}
		//containers to hold the preferred class and seat for the passenger
		int classSelection;
		int seatSelection;
		boolean seatAvailable = false;
		
		//show the airplane layout in a JOption pane by formatting the array into a stringbuilder
		//information found @ http://stackoverflow.com/questions/15388810/java-multidimensional-array-shown-in-optionpane
		//and oracle docs on stringbuilder http://docs.oracle.com/javase/7/docs/api/java/lang/StringBuilder.html
				
		int x, y;
		StringBuilder builder = new StringBuilder();
		for (x = 0;  x < 4; x++)
		{
		for (y = 0; y < 4; y++)
		{
		builder.append(seats[x][y] + " ");
		}
		builder.append("\n");
		}
		JOptionPane.showMessageDialog(null, builder,"Airplane Seat Assignment",JOptionPane.INFORMATION_MESSAGE);
		
		//call class & seat selection methods
		classSelection = promptClassSelect();
		seatSelection = promptSeatSelect();
		
		//If passenger picked first class
		if(classSelection == 1) 
		{
			seatAvailable = searchFirstClass();
			
			//nested if - if seat in FC available
			if(seatAvailable == true)
				{
					firstClassSeatAssignment(classSelection, seatSelection);
				}
			
			//else check the other class with permission
			else
			{
				getPassengerPermission(1);
			}
		}
		
		//else if passenger choose economy class
		else if(classSelection == 2)
		{
			seatAvailable = searchEconomyClass();
			
			if(seatAvailable == true)
			{
				economyClassSeatAssignment(classSelection, seatSelection);
			}
			else
			{
				getPassengerPermission(2);
			}
		}
		
		else
		{
			JOptionPane.showConfirmDialog(null, "You seem to have entered an invalid class selection. This may be a bug.");
		}
		
		//ask the passenger if they want to book another flight
		int dialogResult = JOptionPane.showConfirmDialog(null, "Would you like to book another flight?");
		
		if(dialogResult == JOptionPane.YES_OPTION){
			start();
		}
		
		else
		{
			//ask if the user would like a passenger report
			int dialogResult2 = JOptionPane.showConfirmDialog(null, "Would you like to display a passenger report?");
			if(dialogResult2 == JOptionPane.YES_OPTION)
			{
				displayReport();
			}
			else
			{
				System.exit(0);
			}
		}
	} //end start method
	
	//Method to prompt the passenger for a class selection	
	public int promptClassSelect()
	{
				
		String tempClassSelection = JOptionPane.showInputDialog("Please type 1 for first class or 2 for economy: ");
			
		//handles a cancel	
		if(tempClassSelection == null)
		{
			promptExit();
		}
				
		if(!validate(tempClassSelection) || tempClassSelection.isEmpty()) 
		{
			JOptionPane.showMessageDialog(null, "You have not made a valid entry");
			start();
		}
		
		int classSelection = Integer.parseInt(tempClassSelection);
		
		if(classSelection != 1 && classSelection != 2)
		{
			JOptionPane.showMessageDialog(null, "You have not made a valid entry");
			start();
		}
	
		return classSelection;
		
	}
	
	//Method to prompt the passenger to make a seat preference selection
	public int promptSeatSelect()
	{
				
		String tempSeatSelection = JOptionPane.showInputDialog("Please type 1 for a window seat or 2 for isle seat: ");
		
		//handles a cancel
		if(tempSeatSelection == null)
		{
			promptExit();
		}
		
		if(!validate(tempSeatSelection))
		{
			JOptionPane.showMessageDialog(null, "You have not made a valid entry");
			promptClassSelect();
		}
		
		int seatSelection = Integer.parseInt(tempSeatSelection);
		 
		if(seatSelection != 1 && seatSelection != 2)
		{
			JOptionPane.showMessageDialog(null, "You have not made a valid entry");
			promptSeatSelect();
		}
		
		return seatSelection;
		
	}
	
	//search first class for seats - if no seats ask to search other class
	public boolean searchFirstClass()
	{
		for(int i=0;i<4;i++)
		{	
			if(seats[0][i] == false)
			{
				return true;				
			}
			else if(seats[1][i] == false)
			{
				return true;				
			}
			
		}
		
		return false;
					
	}
	
	//search economy class for seats - if no seats ask to search other class
	public boolean searchEconomyClass()
	{
		for(int i=0;i<4;i++)
		{	
			if(seats[2][i] == false)
			{
				return true;			
			}
			else if(seats[3][i] == false)
			{
				return true;			
			}
			
		}
		return false;
		
	}
	
	//search for seats with passenger preference in first class
	public void firstClassSeatAssignment(int classSelection, int seatSelection)
	{
		int row = 0;
		int col = 0;
		
		//if the selection was window seat		
		if(seatSelection == 1)
		{
			for(int i=0;i<2;i++)
			{
				if(seats[i][0] == false)
				{
					seats[i][0] = true;
					row = i;
					col = 0;
					break;
				}
				else if(seats[i][3] == false)
				{
					seats[i][3] = true;
					row = i;
					col = 3;
					break;
				}
				else
				{
					for(int ii=0;ii<2;ii++)
					{
						if(seats[ii][1] == false)
						{
							seats[i][1] = true;
							row = ii;
							col = 1;
							break;
						}
						else if(seats[ii][2] == false)
						{
							seats[i][2] = true;
							row = ii;
							col = 2;
							break;
						}	
					}
				}
			}	//end for		
			
		} //end if
		
		
		// if the selection was isle seat
		if(seatSelection == 2)
		{
			for(int i=0;i<2;i++)
			{
				if(seats[i][1] == false)
				{
					seats[i][1] = true;
					row = i;
					col = 1;
					break;
				}
				else if(seats[i][2] == false)
				{
					seats[i][2] = true;
					row = i;
					col = 2;
					break;
				}				
				else
				{
					for(int ii=0;ii<2;ii++)
					{
						if(seats[ii][0] == false)
						{
							seats[ii][0] = true;
							row = ii;
							col = 0;
							break;
						}
						else if(seats[ii][3] == false)
						{
							seats[ii][3] = true;
							row = ii;
							col = 3;
							break;
						}
				}
				}
				
			}	//end for		
			
			
		} //end if
		
		displaySeatAssignment(row, col);
		
				
	}
	
	//search for seats with passenger preference in economy class
	public void economyClassSeatAssignment(int classSelection, int seatSelection)
	{
		int row = 0;
		int col = 0;
		
		//if the selection was window seat		
		if(seatSelection == 1)
		{
			for(int i=2;i<4;i++)
			{
				if(seats[i][0] == false)
				{
					seats[i][0] = true;
					row = i;
					col = 0;
					break;
				}
				else if(seats[i][3] == false)
				{
					seats[i][3] = true;
					row = i;
					col = 3;
					break;
				}		
				
			}	//end for		
			
		} //end if
		
		
		// if the selection was isle seat
		if(seatSelection == 2)
		{
			for(int i=2;i<4;i++)
			{
				if(seats[i][1] == false)
				{
					seats[i][1] = true;
					row = i;
					col = 1;
					break;
				}
				else if(seats[i][2] == false)
				{
					seats[i][2] = true;
					row = i;
					col = 2;
					break;
				}				
					
				
			}	//end for		
			
			
		} //end if
		
		displaySeatAssignment(row, col);
				
	}
	
	//if there are no seats in the desired class the passenger is asked if its ok to search in another class
	public void getPassengerPermission(int classNum)
	{
		
		int dialogResult = JOptionPane.showConfirmDialog(null, "There are no seats available in your preferred class. click YES to check for a seat in another class. Click NO or CANCEL to start over.");
		
		if(dialogResult == JOptionPane.YES_OPTION){
			if(classNum == 1)
				searchEconomyClass();
			else
				searchFirstClass();
		}
		else		
			start(); 		
	}		
	
	//formats the seat assignment and displays to passenger
	public void displaySeatAssignment(int row, int col) 
	{
		//ask passenger for name
		String currentPassenger = JOptionPane.showInputDialog("Please enter your first and last name");
		
		if(currentPassenger == null)
		{
			promptExit();
		}

		if(!validateName(currentPassenger) || currentPassenger.isEmpty())
		{
			JOptionPane.showMessageDialog(null, "You have not entered a valid name.");
			displaySeatAssignment(row, col);			
		}
		
		passengersAndSeats[numOfPassengers] = currentPassenger +" " +row +col;
		
		numOfPassengers += 1;
		
		JOptionPane.showMessageDialog(null,"You have been booked in seat "+row +col +". enjoy your flight.");
		
		//show the airplane layout in a JOption pane by formatting the array into a stringbuilder
		//information found @ http://stackoverflow.com/questions/15388810/java-multidimensional-array-shown-in-optionpane
		//and oracle docs on stringbuilder http://docs.oracle.com/javase/7/docs/api/java/lang/StringBuilder.html
		
		int x, y;
	    StringBuilder builder = new StringBuilder();
	    for (x = 0;  x < 4; x++)
	    {
	        for (y = 0; y < 4; y++)
	        {
	            builder.append(seats[x][y] + " ");
	        }
	        builder.append("\n");
	    }
	    JOptionPane.showMessageDialog(null, builder,"Airplane Seat Assignment",JOptionPane.INFORMATION_MESSAGE);
			
	}

	//displays a report showing the list of passengers with seat number in alphabetical order
	public void displayReport()
	{
		//use arrays util to sort the array alphabetically 
		Arrays.sort(passengersAndSeats);
		
		//Learned about Jlist from this stack overflow: http://stackoverflow.com/questions/10869374/how-can-i-print-an-array-in-joptionpane
		JOptionPane.showMessageDialog(null, new JList<Object>(passengersAndSeats));
		
		promptExit();
		
	}

	//an exit prompt 
	public void promptExit()
	{
		int dialogResult = JOptionPane.showConfirmDialog(null, "Would you like to exit the program?");
		
		if(dialogResult == JOptionPane.YES_OPTION){
			System.exit(0);;
		}
		else
		{
			start();
		}
	}
	
	//validation for the class & seat select
	public boolean validate(String input)//true if valid
	{
		//loop through string verifying for numbers
		for(int i = 0;i<input.length(); i++)
		{
			char c = input.charAt(i);
			if(!Character.isDigit(c))
			{
				return false;
			}	
		}
		return true;
	}
	
	//validation for name input
	public boolean validateName(String input)//true if valid
	{
		//loop through string verifying for numbers
		for(int i = 0;i<input.length(); i++)
		{
			char c = input.charAt(i);
			
			if(!Character.isAlphabetic(c))
			{
			    if(c == '-' || c == ' ')
				{
				return true;					
				}
			    else return false;
			}	
			
		}
		return true;
	}
	
} //end public class
