import java.sql.*;
import java.lang.ClassNotFoundException;
import java.net.InetAddress;

public class SQL_Helper
{
	//create account	
	public boolean Create_Account(String username, String password)
	{
		Connection connection= null;
		Statement statement_1 = null;
		Statement statement_2 = null;
		ResultSet resultset= null;
		
		
		
		try
		{
			Class.forName("com.mysql.jdbc.Driver");			 //Connects the SQL Driver
			connection= DriverManager.getConnection("jdbc:mysql://localhost:3306/poker_db","root","");
			statement_1= connection.createStatement();
			statement_2= connection.createStatement();
			resultset=statement_1.executeQuery("SELECT * FROM poker_users WHERE username='"+username+"' ");
			while(resultset.isClosed()==false)
			{
				statement_2.execute("INSERT INTO poker_users(username, password, chips) VALUES('"+username+"', '"+password +"', '10000')");
				return true;
			}
			return false;
						
		}
		
		
		catch( ClassNotFoundException error)
		{
			System.out.println("Error: " + error.getMessage());
		}
		catch( SQLException error)
		{
			return false;
			
		}
		
		finally
		{
			if (connection!= null)  try{connection.close();} catch(SQLException ignore){}
			if (statement_1 !=null) try{statement_1.close();} catch(SQLException ignore){}	
			if (statement_2 !=null) try{statement_2.close();} catch(SQLException ignore){}	
			if (resultset !=null) try{statement_1.close();} catch(SQLException ignore){}
		}
		return false;
	}
	
	//login check	
	public  boolean Login(String username, String password)
	{
		Connection connection= null;
		Statement statement = null;
		ResultSet resultset= null;
		
		try
		{
			Class.forName("com.mysql.jdbc.Driver");			 //Connects the SQL Driver
			connection= DriverManager.getConnection("jdbc:mysql://localhost:3306/poker_db","root","");
			statement= connection.createStatement();
			resultset=statement.executeQuery("SELECT * FROM poker_users WHERE username='"+username+"' ");
			
			
			while(resultset.next())
			{
				if(password.equals(resultset.getString("password")))
				{
					System.out.println("Login Successful");
					return true;
				}
				else
					return false;
											
			}
			if(resultset.next())
			{
				return false;
			}
			
			
		}
		
		catch( ClassNotFoundException error)
		{
			System.out.println("Error: " + error.getMessage());
		}
		catch( SQLException error)
		{
			System.out.println("Error:"+ error.getMessage());
			return false;
		}
		finally
		{
			if (connection!= null)  try{connection.close();} catch(SQLException ignore){}
			if (statement !=null) try{statement.close();} catch(SQLException ignore){}	
			if (resultset !=null) try{statement.close();} catch(SQLException ignore){}
		}
		
		return false;
	}
	
	//update	
	public  boolean Update(String username, int chips)
	{
		Connection connection= null;
		Statement statement_1 = null;
		Statement statement_2 = null;
		ResultSet resultset= null;
				
	
		
		try
		{
			Class.forName("com.mysql.jdbc.Driver");			 //Connects the SQL Driver
			connection= DriverManager.getConnection("jdbc:mysql://localhost:3306/poker_db","root","");
			statement_1= connection.createStatement();
			statement_2= connection.createStatement();
			resultset=statement_1.executeQuery("SELECT * FROM poker_users WHERE username='"+username+"' ");
			while(resultset.next())
			{
				statement_2.execute("UPDATE poker_users SET chips='"+chips+"' WHERE username='"+username+"'");
				return true;
			}
			if(!resultset.next())
			{
				return false;
			}
			return false;
			
		}
		
		catch( ClassNotFoundException error)
		{
			System.out.println("Error: " + error.getMessage());
		}
		catch( SQLException error)
		{
			System.out.println("Error:"+ error.getMessage());
		}
		finally
		{
			if (connection!= null)  try{connection.close();} catch(SQLException ignore){}
			if (statement_1 !=null) try{statement_1.close();} catch(SQLException ignore){}	
			if (statement_2 !=null) try{statement_2.close();} catch(SQLException ignore){}
			if (resultset !=null) try{statement_1.close();} catch(SQLException ignore){}
		}
		return false;
	}
	
	//table lookup
	public int[] Table_Lookup(int type)
	{
		Connection connection= null;
		Statement statement_1 = null;
		Statement statement_2 = null;
		ResultSet resultset= null;
		int[] available_tables= new int[100];
		int i=0;		
		int j=0;
	
		
		try
		{
			Class.forName("com.mysql.jdbc.Driver");			 //Connects the SQL Driver
			connection= DriverManager.getConnection("jdbc:mysql://localhost:3306/poker_db","root","");
			statement_1= connection.createStatement();
			statement_2= connection.createStatement();
			resultset=statement_1.executeQuery("SELECT * FROM poker_tables WHERE TYPE='"+type+"' AND PLAYERS <5 ");
			if(!resultset.next())
			{
				statement_2.execute("INSERT INTO poker_tables (TYPE, PLAYERS) VALUES ("+type+", 0)");
			}
			resultset=statement_1.executeQuery("SELECT * FROM poker_tables WHERE TYPE='"+type+"' AND PLAYERS <5 ");
			while(resultset.next())
			{
				available_tables[i]=resultset.getInt("ID");
				i++;
			}
			int[] temporary_tables= new int[i];
			for(j=0;j<i;j++)
			{
				temporary_tables[j]=available_tables[j];
			}
			available_tables=temporary_tables;
			
		}
		
		catch( ClassNotFoundException error)
		{
			System.out.println("Error: " + error.getMessage());
		}
		catch( SQLException error)
		{
			System.out.println("Error:"+ error.getMessage());
		}
		finally
		{
			if (connection!= null)  try{connection.close();} catch(SQLException ignore){}
			if (statement_1 !=null) try{statement_1.close();} catch(SQLException ignore){}	
			if (statement_2 !=null) try{statement_2.close();} catch(SQLException ignore){}
			if (resultset !=null) try{statement_1.close();} catch(SQLException ignore){}
		}
		return available_tables;
	}

	//Table Join
	public boolean Table_Join(int ID)
	{
		Connection connection= null;
		Statement statement_1 = null;
		Statement statement_2 = null;
		ResultSet resultset= null;
		int player_count=0;
				
	
		
		try
		{
			Class.forName("com.mysql.jdbc.Driver");			 //Connects the SQL Driver
			connection= DriverManager.getConnection("jdbc:mysql://localhost:3306/poker_db","root","");
			statement_1= connection.createStatement();
			statement_2= connection.createStatement();
			resultset=statement_1.executeQuery("SELECT PLAYERS FROM poker_tables WHERE ID="+ID+" AND PLAYERS < 5");
			while(resultset.next())
			{
				player_count=resultset.getInt("PLAYERS")+1;
				statement_2.execute("UPDATE poker_tables SET PLAYERS='"+player_count+"' WHERE ID='"+ID+"'");
				return true;
			}
			if(!resultset.next())
			{
				return false;
			}
			return false;
			
		}
		
		catch( ClassNotFoundException error)
		{
			System.out.println("Error: " + error.getMessage());
		}
		catch( SQLException error)
		{
			System.out.println("Error:"+ error.getMessage());
		}
		finally
		{
			if (connection!= null)  try{connection.close();} catch(SQLException ignore){}
			if (statement_1 !=null) try{statement_1.close();} catch(SQLException ignore){}	
			if (statement_2 !=null) try{statement_2.close();} catch(SQLException ignore){}
			if (resultset !=null) try{statement_1.close();} catch(SQLException ignore){}
		}
		return false;
	}
	
	public int Get_Chip_Count(String username)
	{
		Connection connection= null;
		Statement statement_1 = null;
		Statement statement_2 = null;
		ResultSet resultset= null;
		int chips;		
	
		
		try
		{
			Class.forName("com.mysql.jdbc.Driver");			 //Connects the SQL Driver
			connection= DriverManager.getConnection("jdbc:mysql://localhost:3306/poker_db","root","");
			statement_1= connection.createStatement();
			statement_2= connection.createStatement();
			resultset=statement_1.executeQuery("SELECT chips FROM poker_users WHERE username='"+username+"' ");
			while(resultset.next())
			{
				chips=resultset.getInt("chips");
				return chips;
		
			}
			if(!resultset.next())
			{
				return 0;
			}
		
			
		}
		
		catch( ClassNotFoundException error)
		{
			System.out.println("Error: " + error.getMessage());
		}
		catch( SQLException error)
		{
			System.out.println("Error:"+ error.getMessage());
		}
		finally
		{
			if (connection!= null)  try{connection.close();} catch(SQLException ignore){}
			if (statement_1 !=null) try{statement_1.close();} catch(SQLException ignore){}	
			if (statement_2 !=null) try{statement_2.close();} catch(SQLException ignore){}
			if (resultset !=null) try{statement_1.close();} catch(SQLException ignore){}
		}
		return 0;
		
		
	}
}
	
