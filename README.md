# japanese
A KOTLIN AND FOR ANDROID little dictionary-like, matching game to learn vocabulary in japanese using romaji (japanese to spanish).
I USE SPANISH ON THE APP BUT ITS ACTUALLY A WORD-MATCHING APP

Its a game where you add a word (i entered a small amount of spanish words) to translate into japanese (using latin letters,
romaji) and a meaning for that word.
After, you are asked for a number of questions (you choose how many), so you can answer the question and the system will 
tell you if you missed or you are right by signaling it with colors and giving you the answer aswell.

NOTE:
The class located in "com.juan.japones.fragments" and named "EnterNewWordsFragment" contains a function called
"enterIt()", which is commented out, and it gets called in the same class, at the end of the "OnCreateView()" callback (this
 calling is also commented out). This function enters a number of words into the database so you can start using the app
 without having to enter more. 
 However, this words are in spanish language but you can use any language you want though.
 To enter this words, just go into the EnterNewWordsFragment using Android Studio, uncomment the enterIt() function and the
 calling, execute the app and press the button that says "ANADIR PALABRA NUEVA", that will add the dictionary to the database.
 Now stop the app, comment the method and its calling again and restart the app. From now on, you can add them 
 manually straight in the app or add more words in the same format used in the "enterIt()" method and after erasing the database,
  you can add everything again with your addons.



Contains a bug that will make the app crash after erasing selected wors and trying to select new ones. This is due to the system not knowing the new number of elements on the list. 
