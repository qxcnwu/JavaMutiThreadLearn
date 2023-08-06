#!/bin/bash
BACKUP="/home/AutoDataFrameWork/backup"
TASK="/home/AutoDataFrameWork/Task"
DATATIME=$(date +%Y-%m-%d_%H:%M:%S)
DATA=$(date -d "2 days ago" +%Y-%m-%d)
echo "$DATATIME"
FILENAME=${BACKUP}/"$DATATIME".json
TASKNAME=${TASK}/"$DATATIME".json
touch "$FILENAME"
echo "$FILENAME"
echo "{
        \"downloadCommand\": {
          \"AREA\": \"china\",
          \"DIR\": \"/home/AutoDataFrameWork/download/hdf\",
          \"PRODUCTION\": \"MOD04_3K\",
          \"TIME\": \"$DATA\"
        },
        \"imageConvertCommand\": {
          \"BINPATH\": \"/home/AutoDataFrameWork/download/bin\",
          \"HDFDIR\": \"/home/AutoDataFrameWork/download/hdf/\",
          \"PICDIR\": \"/home/AutoDataFrameWork/download/pic\",
          \"PTTR\": \"Image_Optical_Depth_Land_And_Ocean\",
          \"THREAD_NUM\": \"6\",
          \"TIMES\": \"$DATA\"
        },
        \"netWorkCommand\": {
          \"IP\": \"159.75.14.197\",
          \"PORT\": 16578,
          \"TIMES\": \"$DATA\",
          \"BINPATH\": \"/home/AutoDataFrameWork/download/bin\",
          \"PICPATH\": \"/home/AutoDataFrameWork/download/pic\",
          \"PERFIX\": \"C\",
          \"PERFIX2\": \"D\"
        }
      }" > $FILENAME
cp $FILENAME $TASKNAMEA
echo "Done"