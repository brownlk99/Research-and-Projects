library(ez) #for doing the ANOVAs
library(dplyr) # great library for massaging data
library(ggplot2)

data = read.csv("/Users/lukebrown/Desktop/Q370/Final Project/speedAndLearningTotal.csv", stringsAsFactors=TRUE,sep=',', header = TRUE)
model<-ezANOVA(data=data,dv=numCorrect,within=c(speed),wid=subject)
model
model2<-ezANOVA(data=data,dv=numCorrect,within=c(video),wid=subject)
model2

table1 <- tapply(X=data$numCorrect,INDEX=list(data$speed),FUN=mean)
table1

table2 <- tapply(X=data$numCorrect,INDEX=list(data$video),FUN=mean)
table2

table4 <- tapply(X=data$numCorrect,INDEX=list(data$speed, data$video),FUN=mean)
table4

table5 <- tapply(X=data$numCorrect,INDEX=list(data$wpm),FUN=mean)
table5

table6 <- tapply(X=data$numCorrect,INDEX=list(data$subject, data$video),FUN=mean)
table6

table7 <- tapply(X=data$wpm,INDEX=list(data$video, data$speed),FUN=mean)
table7

ggplot(data,aes(x=speed,y=numCorrect,fill=speed))+stat_summary(fun=mean,geom="bar")+stat_summary(fun.data=mean_cl_normal,geom="errorbar")+ xlab("Talking Speed") + ylab("Number of Correct")

ggplot(data,aes(x=video,y=numCorrect,fill=video))+stat_summary(fun=mean,geom="bar")+stat_summary(fun.data=mean_cl_normal,geom="errorbar")+ xlab("Video") + ylab("Number of Correct")

ggplot(data,aes(x=wpm,y=numCorrect,fill=video))+stat_summary(fun=mean,geom="bar")+stat_summary(fun.data=mean_cl_normal,geom="errorbar")+ xlab("Words Per Minute") + ylab("Number of Correct")


cor.test(data$numCorrect,data$wpm)
plot(data$wpm, data$numCorrect, pch=16,col=data$subject,xlab="Words Per Minute",ylab="Number Correct")

byVideoA <- filter(data, video == "A")
byVideoB <- filter(data,video=="B")
byVideoC <- filter(data,video=="C")
byVideoA
byVideoB
byVideoC

bySpeedOne <- filter(data,speed=="one") 
bySpeedOneHalf <- filter(data,speed=="one&half")
bySpeedTwo <- filter(data,speed=="two")


byVideoAndSpeedOneA <- filter(byVideoA, speed == "one")
byVideoAndSpeedOneHalfA <- filter(byVideoA, speed == "one&half")
byVideoAndSpeedTwoA <- filter(byVideoA, speed == "two")

byVideoAndSpeedOneB <- filter(byVideoB, speed == "one")
byVideoAndSpeedOneHalfB <- filter(byVideoB, speed == "one&half")
byVideoAndSpeedTwoB <- filter(byVideoB, speed == "two")

byVideoAndSpeedOneC <- filter(byVideoC, speed == "one")
byVideoAndSpeedOneHalfC <- filter(byVideoC, speed == "one&half")
byVideoAndSpeedTwoC <- filter(byVideoC, speed == "two")


ggplot(byVideoA,aes(x=subject,y=numCorrect,fill=video))+stat_summary(fun=mean,geom="bar")+stat_summary(fun.data=mean_cl_normal,geom="errorbar")+ xlab("Subject") + ylab("Number Correct")
ggplot(byVideoB,aes(x=subject,y=numCorrect,fill=video))+stat_summary(fun=mean,geom="bar")+stat_summary(fun.data=mean_cl_normal,geom="errorbar")+ xlab("Subject") + ylab("Number Correct")
ggplot(byVideoC,aes(x=subject,y=numCorrect,fill=video))+stat_summary(fun=mean,geom="bar")+stat_summary(fun.data=mean_cl_normal,geom="errorbar")+ xlab("Subject") + ylab("Number Correct")

ggplot(data,aes(x=subject,y=numCorrect,fill=speed))+stat_summary(fun=mean,geom="bar")+stat_summary(fun.data=mean_cl_normal,geom="errorbar")+ xlab("Subject") + ylab("Number Correct")

ggplot(byVideoAndSpeedOneA,aes(x=subject,y=numCorrect,fill=video))+stat_summary(fun=mean,geom="bar")+stat_summary(fun.data=mean_cl_normal,geom="errorbar")+ xlab("Subject") + ylab("Number Correct at 1x")
ggplot(byVideoAndSpeedOneHalfA,aes(x=subject,y=numCorrect,fill=video))+stat_summary(fun=mean,geom="bar")+stat_summary(fun.data=mean_cl_normal,geom="errorbar")+ xlab("Subject") + ylab("Number Correct at 1.5x")
ggplot(byVideoAndSpeedTwoA,aes(x=subject,y=numCorrect,fill=video))+stat_summary(fun=mean,geom="bar")+stat_summary(fun.data=mean_cl_normal,geom="errorbar")+ xlab("Subject") + ylab("Number Correct at 2x")
ggplot(byVideoAndSpeedOneB,aes(x=subject,y=numCorrect,fill=video))+stat_summary(fun=mean,geom="bar")+stat_summary(fun.data=mean_cl_normal,geom="errorbar")+ xlab("Subject") + ylab("Number Correct at 1x")
ggplot(byVideoAndSpeedOneHalfB,aes(x=subject,y=numCorrect,fill=video))+stat_summary(fun=mean,geom="bar")+stat_summary(fun.data=mean_cl_normal,geom="errorbar")+ xlab("Subject") + ylab("Number Correct at 1.5x")
ggplot(byVideoAndSpeedTwoB,aes(x=subject,y=numCorrect,fill=video))+stat_summary(fun=mean,geom="bar")+stat_summary(fun.data=mean_cl_normal,geom="errorbar")+ xlab("Subject") + ylab("Number Correct at 2x")
ggplot(byVideoAndSpeedOneC,aes(x=subject,y=numCorrect,fill=video))+stat_summary(fun=mean,geom="bar")+stat_summary(fun.data=mean_cl_normal,geom="errorbar")+ xlab("Subject") + ylab("Number Correct at 1x")
ggplot(byVideoAndSpeedOneHalfC,aes(x=subject,y=numCorrect,fill=video))+stat_summary(fun=mean,geom="bar")+stat_summary(fun.data=mean_cl_normal,geom="errorbar")+ xlab("Subject") + ylab("Number Correct at 1.5x")
ggplot(byVideoAndSpeedTwoC,aes(x=subject,y=numCorrect,fill=video))+stat_summary(fun=mean,geom="bar")+stat_summary(fun.data=mean_cl_normal,geom="errorbar")+ xlab("Subject") + ylab("Number Correct at 2x")
