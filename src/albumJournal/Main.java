package albumJournal;
import java.io.*;
import java.util.LinkedList;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		//linkedlist to hold the entire album list
		LinkedList<UnheardAlbum> albumList = new LinkedList<UnheardAlbum>();
		
		Scanner input = new Scanner(System.in);
		boolean Flag = false;
		//main menu next
		while(Flag==false) {
			//options for the menu
			System.out.println("Welcome to the journal of your album backlog-");
			System.out.println("1. Add an album...");
			System.out.println("2. View the backlog...");
			System.out.println("3. Save to file...");
			System.out.println("4. Load from save file...");
			System.out.println("5. Update an album...");
			System.out.println("6. Remove an album...");
			System.out.println("7. Exit...");
			System.out.println("Choose wisely. ");
			
			int choice = input.nextInt();
			input.nextLine();
			
			//Adding an album section
			
			if (choice == 1) {
				System.out.println("\n" + "So first, what's the name of the album? ");
				String title = input.nextLine();
				
				System.out.println("Interesting. Who made it? ");
				String artist = input.nextLine();
				
				System.out.println("Nice. What year did it come out?");
				int year = input.nextInt();
				input.nextLine();
				
				System.out.println("What made you want to listen to this? ");
				String whyListen = input.nextLine();
				
				System.out.println("So why haven't you listened to it yet? ");
				String whyNot = input.nextLine();
				
				System.out.println("I see. Out of 5, how much do you want to listen to it? ");
				int priority = input.nextInt();
				input.nextLine();
				
				System.out.println("If you know what Fantano rated it, put it here. Otherwise, put -1. ");
				int Fantano = input.nextInt();
				input.nextLine();
				
				//so now, we collate all of this information
				
				UnheardAlbum newAlbum = new UnheardAlbum(title, artist, year, whyListen, whyNot, priority, Fantano);
				albumList.add(newAlbum); 
				//added to the list!
				System.out.println("The album has been added! 😎 ");			
			} //end of adding section
			
			else if (choice == 2) { 
				
				//Viewing the collection of albums
				
				if (albumList.isEmpty()) {
					System.out.println("There's nothing here bruh. There is always something new to listen to... ");
				}
				
				else {
					System.out.println("Albums you WILL listen to someday: ");
					
					//Iterate through every entry
					for (int i = 0; i < albumList.size(); i++) {
						UnheardAlbum album = albumList.get(i);
						System.out.print("📡 Fetching streams for " + album.getTitle() + "... ");
			            String streams = fetchSpotifyStreams(album.getTitle(), album.getArtist());
			            System.out.println("Done!");
						System.out.println("Entry " + (i+1) + ": ");
						System.out.println(albumList.get(i).getFullDescription());
						System.out.println(" Total streams: " + streams);
			            System.out.println();
					}	
				
					//ADDING SORTING HERE
					System.out.println("Would you like to sort the list by priority? (Descending) y/n: ");
					char sorter = input.nextLine().charAt(0);
					if (sorter == 'y') { //Sort it
						albumList.sort((a1, a2) -> Integer.compare(a2.getPriority(), a1.getPriority()));
					
		            // Printing the sorted list
		            System.out.println("\n===== SORTED BY PRIORITY (HIGHEST FIRST) =====");
		            for (int i = 0; i < albumList.size(); i++) {
		                System.out.println("\nEntry " + (i + 1) + ": ");
		                System.out.println(albumList.get(i).getFullDescription());
		            }}
		            System.out.println("To save this order, use the save function.");
				}
			} //end of viewing section
			
			else if (choice == 3) { //this saves the current list to a text file
				try {
					PrintWriter writer = new PrintWriter("backlog.txt");
					for (int i = 0; i < albumList.size(); i++) {
						UnheardAlbum album = albumList.get(i);
						//writing in each album
						writer.println(album.getTitle() + "|" +
								album.getArtist() + "|" +
								album.getYear() + "|" +
								album.getWhyListen() + "|" +
								album.getWhyNot() + "|" +
								album.getPriority() + "|" +
								album.getFantano());
						}
					writer.close();
					System.out.println(albumList.size() + " album(s) saved to backlog.txt");
					} 
				catch (IOException e) {
					// TODO: handle exception
					System.out.println("Error saving: " + e.getMessage());
				}
			} //end of saving section
			
			
			else if (choice == 4) { //this is loading data from the save file
				try {
					//open the file
					BufferedReader br = new BufferedReader(new FileReader("backlog.txt"));
					String line;
					int count = 0;
					//read through the file
						while ((line = br.readLine()) != null) {
					//split data by the | symbol
							String[] parts = line.split("\\|"); // | is a special char
					//store each split part
							String title = parts[0];	 
							String artist = parts[1];	 
							int year = Integer.parseInt(parts[2]);	 
							String whyListen = parts[3];	 
							String whyNot = parts[4];	 
							int priority = Integer.parseInt(parts[5]);
							int Fantano = Integer.parseInt(parts[6]);
						 
							UnheardAlbum loadedAlbum = new UnheardAlbum(title, artist, year, whyListen, whyNot, priority, Fantano);
							albumList.add(loadedAlbum);
							count++;
					 }
					br.close();
					System.out.println("Loaded " + count + " album(s) from backlog.txt");
					
					} 
				catch (FileNotFoundException e) {
					// TODO: handle exception 
					System.out.println("No save file found.");
				}
				catch (IOException e) {
					System.out.println("Error loading: " + e.getMessage());
				}
					
			} //end of loading section
			
			else if (choice == 5) { //editing an album's entry
				if (albumList.isEmpty()) {
			        System.out.println("The backlog is empty.");
			    } 
				else {
					System.out.println("Which album do you want to update?"); //making a list with indexes for the user to choose
			        for (int i = 0; i < albumList.size(); i++) {
			            System.out.println((i + 1) + ". " + albumList.get(i).getTitle() + " by " + albumList.get(i).getArtist());
			        }
			        System.out.print("Enter the number: ");
			        int updateIndex = input.nextInt();
			        input.nextLine();
			    	
			        if (updateIndex >= 1 && updateIndex <= albumList.size()) {
			            // get album from the list
			            UnheardAlbum toUpdate = albumList.get(updateIndex - 1);
			            
			            System.out.println("Updating: " + toUpdate.getTitle());
			            System.out.print("New priority (1-5, current is " + toUpdate.getPriority() + "): ");
			            int newPriority = input.nextInt();
			            input.nextLine();
			            toUpdate.setPriority(newPriority); // look i'm using a setter
			            
			            System.out.print("New reason to listen (current: " + toUpdate.getWhyListen() + "): ");
			            String newReason = input.nextLine();
			            toUpdate.setWhyListen(newReason); // another setter
			            
			            System.out.print("New barrier (current: " + toUpdate.getWhyNot() + "): ");
			            String newBarrier = input.nextLine();
			            toUpdate.setWhyNot(newBarrier); // this is prime OOP skills right here
			            
			            System.out.println("✅ Album updated successfully!");
			            System.out.println("Remember to save (option 3) to update your file.");
			        } else {
			            System.out.println("Idk what you're doing, try again.");
			        }
			    }
			} //end of updating review section
			
			else if (choice == 6) { //so this is for deleting a review
				if (albumList.isEmpty()) {
			        System.out.println("The backlog is empty.");
			    } 
				else {
					//make a list with indexes for the user to choose
			        System.out.println("Which album do you want to remove?");
			        for (int i = 0; i < albumList.size(); i++) {
			            System.out.println((i + 1) + ". " + albumList.get(i).getTitle() + " by " + albumList.get(i).getArtist());
			        }
			        System.out.print("Enter the number: ");
			        int deleteIndex = input.nextInt();
			        input.nextLine();
			    	
			        if (deleteIndex >= 1 && deleteIndex <= albumList.size()) {
			            // Remove it
			            UnheardAlbum removed = albumList.remove(deleteIndex - 1);
			            System.out.println("🗑️ Removed: " + removed.getTitle() + " by " + removed.getArtist());
			            System.out.println("Remember to save (option 3) to update your file!");
			        } else {
			            System.out.println("What are you DOING? ");
			        }
			    }
			} //end of removing review section
			
			else if (choice == 7) { //if user chooses to exit
				System.out.println("😒 Good. Go listen to some music.");
				break; //ends the program completely
			}
			
			else { 
			System.out.println("Whatever you typed, it wasn't 1, 2, 3, 4, 5, 6, or 7...");
			}
			
		}
			
		input.close(); //closing the scanner input	

	}
	
//method to fetch spotify streams from python scraper
	public static String fetchSpotifyStreams(String title, String artist) {
	    try { 
	        // Build the command
	        ProcessBuilder pb = new ProcessBuilder("python", "spotify_stats.py", title, artist);
	        
	        // Redirect error stream to the output so we can see errors
	        pb.redirectErrorStream(true);
	        
	        Process p = pb.start();
	        BufferedReader reader = new BufferedReader(
	            new InputStreamReader(p.getInputStream())
	        );
	        
	        // Read ALL output (not just one line)
	        String line;
	        StringBuilder output = new StringBuilder();
	        while ((line = reader.readLine()) != null) {
	            output.append(line).append("\n");
	        }
	        
	        String result = output.toString().trim();
	        
	        if (result.startsWith("ERROR")) {
	            return "Not found";
	        }
	        
	        try {
	            long number = Long.parseLong(result);
	            return String.format("%,d", number);
	        } catch (NumberFormatException e) {
	            return result;
	        }
	        
	    } catch (Exception e) {
	        return "Error: " + e.getMessage();
	    }
	}
	
}
