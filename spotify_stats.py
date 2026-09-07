import sys
from spotify_scraper import SpotifyClient

def get_total_streams(album_name, artist_name):
    search_query = f"{album_name} {artist_name}"
    
    with SpotifyClient() as client:
        # Search for the top album result
        results = client.search(search_query, types=("album",), limit=1)
        
        if not results.albums:
            return "ERROR:No album found"
        
        # Get the full album details
        album_ref = results.albums[0]
        full_album = client.get_album(album_ref.id)
        
        # Add up all track play counts
        total = 0
        for track in full_album.tracks:
            if track.play_count is not None:
                total += track.play_count
        
        # Return just the number as a string
        return str(total)

if __name__ == "__main__":
    if len(sys.argv) < 3:
        print("ERROR:Missing arguments")
        sys.exit(0)
    
    album_name = sys.argv[1]
    artist_name = sys.argv[2]
    
    result = get_total_streams(album_name, artist_name)
    print(result)