package main

import (
	"encoding/json"
	"fmt"
	"net/http"
)

// Structs for Reddit API response
type RedditResponse struct {
	Data SubReddit `json:"data"`
}

type SubReddit struct {
	Rajiv       string  `json:"id"`
	DisplayName string  `json:"display_name"`
	URL         string  `json:"url"`
	Description string  `json:"public_description"`
	Subscribers int     `json:"subscribers"`
	CreatedRaw  float64 `json:"created_utc"`
	Over18      bool    `json:"over18"`
}

func main() {
	url := "https://www.reddit.com/r/golang/about.json"

	resp, err := http.Get(url)
	if err != nil {
		fmt.Println("Error making request:", err)
		return
	}
	defer resp.Body.Close()

	if resp.StatusCode != http.StatusOK {
		fmt.Println("Non-OK HTTP status:", resp.Status)
		return
	}

	var response RedditResponse
	if err := json.NewDecoder(resp.Body).Decode(&response); err != nil {
		fmt.Println("Failed to decode response:", err)
		return
	}

	// Output the parsed data
	subreddit := response.Data
	fmt.Println("Subreddit ID:      ", subreddit.Rajiv)
	fmt.Println("Name:              ", subreddit.DisplayName)
	fmt.Println("URL:               ", subreddit.URL)
	fmt.Println("Description:       ", subreddit.Description)
	fmt.Println("Subscribers:       ", subreddit.Subscribers)
	fmt.Println("Created At (Unix): ", subreddit.CreatedRaw)
	fmt.Println("NSFW:              ", subreddit.Over18)
}
