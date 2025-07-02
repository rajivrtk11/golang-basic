// package main

// import (
// 	"encoding/json"
// 	"fmt"
// 	"log"
// 	"net/http"
// 	"net/url"
// )

// // "ZuQD6l8PX0Fm23j-3hhqhQ",
// //
// //	"taSVcXUSLW_jnQJjMIclgOmOAlfriA",
// var (
// 	clientID     = "ZuQD6l8PX0Fm23j-3hhqhQ"
// 	clientSecret = "taSVcXUSLW_jnQJjMIclgOmOAlfriA"
// 	redirectURI  = "http://localhost:8080/callback"
// 	userAgent    = "go:reddit-auth-app:v1.0.0 (by /u/yourusername)"
// 	state        = "random_state_string" // Change for production
// )

// // Generates the Reddit OAuth URL
// func getRedditAuthURL(w http.ResponseWriter, r *http.Request) {
// 	authURL := fmt.Sprintf(
// 		// https://www.reddit.com/api/v1/authorize?client_id=ZuQD6l8PX0Fm23j-3hhqhQ&duration=permanent&redirect_uri=http%3A%2F%2Flocalhost%3A8787%2Fdoota.portal.v1.PortalService%2FHandleRedditCallback&response_type=code&scope=identity&state=some-random-state
// 		"https://www.reddit.com/api/v1/authorize?client_id=ZuQD6l8PX0Fm23j-3hhqhQ&response_type=code&state=random_state_string&redirect_uri=http%3A%2F%2Flocalhost%3A8080%2Fcallback&duration=temporary&scope=identity,submit,read",
// 		url.QueryEscape(clientID),
// 		url.QueryEscape(state),
// 		url.QueryEscape(redirectURI),
// 	)

// 	json.NewEncoder(w).Encode(map[string]string{"auth_url": authURL})
// }

// // http://localhost:8787/doota.portal.v1.PortalService/HandleRedditCallback

// // Handles the callback from Reddit
// func handleRedditCallback(w http.ResponseWriter, r *http.Request) {
// 	query := r.URL.Query()
// 	code := query.Get("code")
// 	returnedState := query.Get("state")

// 	if returnedState != state {
// 		http.Error(w, "Invalid state", http.StatusBadRequest)
// 		return
// 	}

// 	// Exchange code for token
// 	data := url.Values{}
// 	data.Set("grant_type", "authorization_code")
// 	data.Set("code", code)
// 	data.Set("redirect_uri", redirectURI)

// 	req, err := http.NewRequest("POST", "https://www.reddit.com/api/v1/access_token", nil)
// 	if err != nil {
// 		http.Error(w, "Failed to create request", http.StatusInternalServerError)
// 		return
// 	}

// 	req.SetBasicAuth(clientID, clientSecret)
// 	req.Header.Set("User-Agent", userAgent)
// 	req.Header.Set("Content-Type", "application/x-www-form-urlencoded")
// 	req.Body = http.NoBody

// 	// Manually write data into body
// 	req.URL.RawQuery = data.Encode()
// 	client := &http.Client{}
// 	resp, err := client.Do(req)
// 	if err != nil {
// 		http.Error(w, "Failed to get access token", http.StatusInternalServerError)
// 		return
// 	}
// 	defer resp.Body.Close()

// 	var result map[string]interface{}
// 	json.NewDecoder(resp.Body).Decode(&result)
// 	fmt.Println("the token data is", result)
// 	json.NewEncoder(w).Encode(result)
// }

// // go run . tools integrations vapi create f47ac10b-58cc-4372-a567-0e02b2c3d479 '{"foo":"bar"}' --pg-dsn "postgres://user:pass@localhost:5432/dbname?sslmode=disable"

// func main() {
// 	http.HandleFunc("/auth/reddit", getRedditAuthURL)
// 	http.HandleFunc("/callback", handleRedditCallback)

// 	fmt.Println("Server running at http://localhost:8080")
// 	log.Fatal(http.ListenAndServe(":8080", nil))
// }
