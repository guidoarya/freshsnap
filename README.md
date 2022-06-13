# FreshSnap - Fruits and Vegetables Quality Detection
![freshsnap-logo](https://user-images.githubusercontent.com/73012972/173266996-7f98b804-9da8-42e6-8a49-39af240f960e.png)

FreshSnap is a shopping companion application, which is expected to help user to detect the quality of fruit and vegetables by simply capturing pictures through the camera or upload pictures stored in the smartphone gallery. After the user capture or upload image of fruit or vegetable, our application will predict the freshness percentage of the fruits and vegetables, how to keep them, and food reference based on the prediction. we also add a location information feature so that all users can find out which market with good quality ingredients and which with the bad ones.

## Technology
* Google Colab
* TensorFlow

## Dataset 
* https://www.kaggle.com/datasets/alibaloch/vegetables-fruits-fresh-and-stale
* https://www.kaggle.com/datasets/shivanir23/good-and-bad-fruits-ieee-extended

## Machine Learning
To develop this machine learning model. we tried to train our model with Convolutional Neural Network and Transfer Learning. We find the best model to predict by using Convolutional Neural Network as our final model. 

1. Download and combining two datasets from Kaggle for the variation to balance the training and testing data
2. Preprossesing Data using Image Augmentation
3. Train the based model with Convolutional Neural Network
4. Plotting the model with Graph 
5. Create the prediction added by the percentage result as the confidence level
6. Save the final model and convert into tflite
