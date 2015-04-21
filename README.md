# GibbsLDA
Clone Project

git clone https://github.com/YuhaoZhu/GibbsLDA.git



Run Training Jar

java -jar GibbsLDA.jar documentsPath ModelPath iter topicNum saveATIter alpha

Example:

nohup java -jar -Xmx1024m GibbsLDA.jar result/ model200/ 100 150 80 0.01 > log &

