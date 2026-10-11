#!/usr/bin/env bash
# ====================================================================
# Career Compass - REST API Manual Test Commands (cURL)
# ====================================================================

BASE_URL="http://localhost:8080/api"

echo "=========================================================="
echo "1. Testing GET /api/assessment/questions (15 Questions)"
echo "=========================================================="
curl -s -X GET "${BASE_URL}/assessment/questions" | jq . || curl -s -X GET "${BASE_URL}/assessment/questions"

echo -e "\n\n=========================================================="
echo "2. Testing GET /api/careers (5 Career Profiles & Weights)"
echo "=========================================================="
curl -s -X GET "${BASE_URL}/careers" | jq . || curl -s -X GET "${BASE_URL}/careers"

echo -e "\n\n=========================================================="
echo "3. Testing GET /api/assessment/results/1 (Demo Assessment)"
echo "=========================================================="
curl -s -X GET "${BASE_URL}/assessment/results/1" | jq . || curl -s -X GET "${BASE_URL}/assessment/results/1"

echo -e "\n\n=========================================================="
echo "4. Testing POST /api/assessment/submit (Software Dev Submission)"
echo "=========================================================="
curl -s -X POST "${BASE_URL}/assessment/submit" \
     -H "Content-Type: application/json" \
     -d @submit_request_software_dev.json | jq . || \
curl -s -X POST "${BASE_URL}/assessment/submit" \
     -H "Content-Type: application/json" \
     -d @submit_request_software_dev.json

echo -e "\n\n=========================================================="
echo "5. Testing POST /api/assessment/submit Validation Failure"
echo "=========================================================="
curl -s -X POST "${BASE_URL}/assessment/submit" \
     -H "Content-Type: application/json" \
     -d @submit_request_invalid_missing_questions.json | jq . || \
curl -s -X POST "${BASE_URL}/assessment/submit" \
     -H "Content-Type: application/json" \
     -d @submit_request_invalid_missing_questions.json

echo -e "\n\nDone! All API tests executed."
