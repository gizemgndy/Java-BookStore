import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class BookStore implements ActionListener{
	
	//book class is created to hold books to be sold
	public static class Books{
		
		//properties of books class 
        public String name;
        public float price;
    }
	
	//Orders class is created to hold orders
	public static class Orders{
	     
		//properties of books class 
        public Books book;
        public int amount;
        public float totalPrice;
    }
	
	//Objects on the form are created
	JFrame frm;
	JLabel lblGenre=new JLabel("Genre");
	JLabel lblAmount=new JLabel("Amount");
	JTextField txtAmount=new JTextField();
	JButton btnAdd=new JButton("Add");
	JButton btnBuy=new JButton("Buy");
	String genres[]= {"Science[10p]","History[15p]","Drama[5p]","Romance[7p]","Action[13p]"};
	JComboBox cbBook=new JComboBox(genres);
	JRadioButton rdGolden,rdSilver,rdNon;
	ButtonGroup grpCalculate;

	//order array list to keep orders
	static ArrayList<Orders> orderList = new ArrayList<Orders>();
	    
	BookStore(){		
		
		//size and location of objects are adjusted on the form
		frm= new JFrame("Book Store");
				
		lblGenre.setBounds(50, 10, 50, 50);
		
		lblAmount.setBounds(320, 10, 50, 50);
				
		txtAmount.setBounds(300, 50, 100, 20);
				
		btnAdd.setBounds(500, 50, 100, 20);
				
		btnBuy.setBounds(500, 120, 100, 20);
			
		cbBook.setBounds(50, 50,150,20); 
	    
	    
	  //initialize radio buttons
	  rdGolden=new JRadioButton("Golden Member");
	  rdSilver=new JRadioButton("Silver Member");
	  rdNon=new JRadioButton("Non-Member");
	  
	  rdGolden.setBounds(50, 250, 150, 20);
	  rdSilver.setBounds(200, 250, 150, 20);
	  rdNon.setBounds(350, 250, 150, 20);
	  
	  //JPanel for radio buttons
	  JPanel pnlRadioPanel=new JPanel();
	  		
	  pnlRadioPanel.add(rdGolden);
	  pnlRadioPanel.add(rdSilver);
	  pnlRadioPanel.add(rdNon);
	  	
	  //make radio groups
	  grpCalculate=new ButtonGroup();
	  grpCalculate.add(rdGolden);
	  grpCalculate.add(rdSilver);
	  grpCalculate.add(rdNon);
	  		
	  //created objects are added to the form
	  frm.add(cbBook);        
	  frm.setLayout(null);    
	  frm.setSize(700,350);    
	  frm.setVisible(true); 
	  frm.add(lblGenre);
	  frm.add(lblAmount);
	  frm.add(txtAmount);
	  frm.add(btnAdd);
	  frm.add(btnBuy);
	  frm.add(pnlRadioPanel);
	  frm.add(rdGolden);
	  frm.add(rdSilver);
	  frm.add(rdNon);
		
	  //register buttons to handle actions
	  btnAdd.addActionListener(this);
	  btnBuy.addActionListener(this);
	}

	public static void main(String[] args) {
		new BookStore();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
	
		//Function to run when btnAdd is clicked
		if(e.getSource().equals(btnAdd))
		{
			//If the amound field is not entered
			if (txtAmount.getText().isEmpty()) {
				//warning message is displayed
				JOptionPane.showMessageDialog(null, "Please enter an amount","Warning", JOptionPane.WARNING_MESSAGE);
			}
			else {
				//a new book class is created populated
				Books book=new Books();
				
				//the book name is assigned the selected value of cbBook
				book.name=cbBook.getSelectedItem().toString();
				
				//Price assignment is made according to the chosen one in cbBook.
				if(cbBook.getSelectedIndex()==0) {
					book.price=10;
				}
				else if(cbBook.getSelectedIndex()==1) {
					book.price=15;
				}
				else if(cbBook.getSelectedIndex()==2) {
					book.price=5;
				}
				else if(cbBook.getSelectedIndex()==3) {
					book.price=7;
				}
				else {
					book.price=13;
				}
				
				//Orders class is created and filled in order to keep the order
				Orders order=new Orders();
				
				//Amount value is taken from textfield
				order.amount=Integer.parseInt(txtAmount.getText());
				order.book=book;
				
				//The price of the book is calculated according to the unit price and quantity
				float totalPrice = book.price*Integer.parseInt(txtAmount.getText());
				
				//the total discount rate is calculated
				float totalDis = 0;
				if(rdGolden.isSelected())
				{
					//15% discount for gold users
					totalDis = (totalPrice * 15) / 100;
				}
				else if(rdSilver.isSelected())
				{
					//15% discount for silver users
					totalDis = (totalPrice * 10) / 100;
				}
				else if(rdNon.isSelected()) {
					//A warning message is shown if the user is not a member.
					JOptionPane.showMessageDialog(null, "Non-member users cannot shop.","Warning", JOptionPane.WARNING_MESSAGE);
					return;
				}
				else {
					//If the user type is not selected, the warning "you must select the user type" is displayed
					JOptionPane.showMessageDialog(null, "Please select membership type.","Warning", JOptionPane.WARNING_MESSAGE);
					return;					
				}
				
				//The price to be paid is calculated by applying the discount
				order.totalPrice = totalPrice-totalDis;
				
				//the order is added to the list
				orderList.add(order);
				
				//selected are cleared
				clearSelected();
			}	
		}
		
		//Function to run when btnBuy is clicked
		if(e.getSource().equals(btnBuy)) {
			
			//variable to hold the message to show on the screen
			String buyMessage = "";
			
			//total amount to be paid
			float totalPay = 0;
			
			//total number of books
			int totalBookCount = 0;
			
			//Orders with an order list are returned with the for loop
			for (int i = 0; i < orderList.size(); i++) {
				
				//next order object
				Orders ord = orderList.get(i);
				
				//The title of the added book, quantity, unit price, total price and discounted price are prepared to appear on the screen.
				buyMessage += (i + 1) + ". Book \n" +
						"Book Name: "+ ord.book.name + "\n"+
						"Amount:"+ ord.amount + "\n" +
						"Unit Price:" + ord.book.price +" ₺\n" +
						"Total Price:" + ord.book.price * ord.amount +" ₺\n"+
						"Discounted Price:" + ord.totalPrice +" ₺\n \n"; 
				
				//price to be paid is added up for each order
				totalPay += ord.totalPrice;
				
				//the number of books received is collected
				totalBookCount += ord.amount;
			}
			
			//the total number of books and the total price to be paid are added to the end of the message
			buyMessage += "Total Book:" + totalBookCount +"\n";
			buyMessage += "Total Pay:" + totalPay+" ₺";
			
			//message is shown on the screen with the help of Joptionpane
			JOptionPane.showMessageDialog(null, buyMessage,"Warning", JOptionPane.WARNING_MESSAGE);
		}
		
	}
	
	//cleaning selected areas after each book addition
	public void clearSelected()
	{
		txtAmount.setText("");
		cbBook.setSelectedIndex(0);
		rdGolden.setSelected(false);
		rdSilver.setSelected(false);
		rdNon.setSelected(false);
	}
}
