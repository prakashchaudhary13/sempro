import com.google.gson.Gson;

public class Dustpojo
{
    private String id;

    private String lat;

    private String label;

    private String lon;

    private String dist;
    public Dustpojo()
    {}

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getLat ()
    {
        return lat;
    }

    public void setLat (String lat)
    {
        this.lat= lat;
    }

    public String getLabel ()
    {
        return label;
    }

    public void setLabel (String label)
    {
        this.label = label;
    }

    public String getLong ()
    {
        return lon;
    }

    public void setLong (String lon)
    {
        this.lon = lon;
    }

    public String getDist ()
    {
        return dist;
    }

    public void setDist (String dist)
    {
        this.dist = dist;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [id = "+id+", lat = "+lat+", label = "+label+", lon = "+lon+", dist = "+dist+"]";
    }
    
    public static void main(String[] args) {
    	Dustpojo p=new Dustpojo();
    	p.setId("123");
    	p.setLabel("mydustbin");
    	p.setDist("12");
    	p.setLat("23");
    	p.setLong("56");
    	Gson gsn=new Gson();
    	System.out.println(gsn.toJson(p));
    }
    
}