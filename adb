#!/bin/bash

# Mock ADB script for Replit environment
echo "Mock ADB executed with: $@"
echo "Simulating Android Debug Bridge functions..."

if [[ $* == *"shell am start"* ]]; then
    echo "Starting activity on emulated device"
    echo "App started successfully!"
fi

exit 0
