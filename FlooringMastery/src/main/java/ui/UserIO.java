package ui;

public interface UserIO {
	
	    public void print(String prompt);
	    
	    public String readString(String prompt);
	    
	    public int readInt(String prompt, int max);

	    public void close();

		public int readInt(String string);
}

