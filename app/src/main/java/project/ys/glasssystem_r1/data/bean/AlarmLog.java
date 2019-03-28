package project.ys.glasssystem_r1.data.bean;

public class AlarmLog {

    public static String[] ALARM_TAGS = {"残片率/%", "电消耗/kw·h", "水消耗/吨", "煤消耗/吨"};


    private String tag;

    private String log;

    public AlarmLog(){

    }

    public AlarmLog(String tag,String log){
        this.tag =tag;
        this.log =log;

    }
    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }

    @Override
    public String toString() {
        return "AlarmLog{" +
                "tag='" + tag + '\'' +
                ", log='" + log + '\'' +
                '}';
    }
}
