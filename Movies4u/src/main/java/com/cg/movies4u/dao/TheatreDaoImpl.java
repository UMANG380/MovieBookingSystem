package com.cg.movies4u.dao;
import com.cg.movies4u.Dto.Movie;
import com.cg.movies4u.Dto.Screen;
import com.cg.movies4u.Dto.Show;
import com.cg.movies4u.Dto.Theatre;
import java.util.*;
import java.util.Map.Entry;
public class TheatreDaoImpl implements TheatreDao{


	    private static Map<Integer, Theatre> theatres = new HashMap<Integer, Theatre>();

	    
	    public Map<Integer,String> getCities() {

	        Map<Integer,String> cities = new TreeMap<Integer, String>();
	        List<Map.Entry<Integer,Theatre>> temp = new ArrayList<Entry<Integer, Theatre>>(theatres.entrySet());
	        for(int i=0;i<temp.size();i++){
	            cities.put(temp.get(i).getValue().getCityPincode(),temp.get(i).getValue().getCityName());
	        }
	        return cities;
	    }

	    
	    public Map<Integer,Theatre> getTheatres() {
	        return theatres;
	    }

	    
	    public Theatre addTheatre(Theatre theatre) {
	        theatres.put(theatre.getTheatreId(),theatre);
	        return theatre;
	    }

	    
	    public List<Theatre> getTheatres(Integer cityPincode) {
	        List<Theatre> theatreList = new LinkedList<Theatre>();
	        List<Map.Entry<Integer,Theatre>> temp = new LinkedList<Entry<Integer, Theatre>>(theatres.entrySet());
	        for(int i=0;i<temp.size();i++){
	            if(cityPincode.equals(temp.get(i).getValue().getCityPincode())){
	                theatreList.add(temp.get(i).getValue());
	            }
	        }
	        return theatreList;
	    }

	    
	    public Theatre searchTheatre(Integer theatreId) {
	        Theatre theatre = theatres.get(theatreId);
	        return theatre;
	    }

	    
	    public Theatre deleteTheatre(Integer theatreId) {
	        Theatre theatre =theatres.get(theatreId);
	        theatres.remove(theatreId);
	        return theatre;
	    }

	    
	    public Set<Movie> getMovies(Integer cityPincode) {
	        Set<Movie> ret = new HashSet<Movie>();
	        List<Theatre> temp = getTheatres(cityPincode);
	        for (int i=0;i<temp.size();i++){
	            ret.addAll(temp.get(i).getMovies());
	        }
	        return ret;
	    }

	    
	    public List<Movie> getMoviesinTheatre(Integer theatreId) {
	        return theatres.get(theatreId).getMovies();
	    }

	    public Screen searchScreen(Integer theatreId,Integer screenId){
	        for(int i=0;i<theatres.get(theatreId).getScreens().size();i++){
	            if(theatres.get(theatreId).getScreens().get(i).getScreenId().equals(screenId)){
	                return theatres.get(theatreId).getScreens().get(i);
	            }
	        }
	        return null;
	    }
	    
	    public Show addShow(Integer theatreId, Integer screenId, Show show) {
	        Screen sc= searchScreen(theatreId,screenId);
	        if(sc!=null){
	            searchTheatre(theatreId).getMovies().add(show.getCurrentMovie());
	            sc.getShows().add(show);
	            return show;
	        }
	        return null;

	    }

	    
	    public Show deleteShow(Integer theatreId, Integer screenId, Integer showId) {
	        Screen sc= searchScreen(theatreId,screenId);
	        if(sc!=null){
	            for(int i=0;i<sc.getShows().size();i++){
	                if(sc.getShows().get(i).getShowId().equals(showId)){
	                    Show show = sc.getShows().get(i);
	                    searchTheatre(theatreId).getMovies().remove(show.getCurrentMovie());
	                    sc.getShows().remove(show);
	                    return show;
	                }
	            }
	        }
	        return null;
	    }

	    public Show searchShow(Integer theatreId,Integer showId) {
	        Theatre theatre = searchTheatre(theatreId);
	        List<Screen> screens = theatre.getScreens();
	        for(int i=0;i<screens.size();i++) {
	            for(int j=0;j<screens.get(i).getShows().size();j++) {
	                if(screens.get(i).getShows().get(j).getShowId().equals(showId)) {
	                    return screens.get(i).getShows().get(j);
	                }
	            }
	        }
	        return null;

	    }

	    public List<Screen> getScreensInTheatre(Integer theatreId){
	        return searchTheatre(theatreId).getScreens();
	    }


	    
	    public Show updateShow(Integer theatreId,Integer screenId, Show show) {
	        deleteShow(theatreId, screenId, show.getShowId());
	        addShow(theatreId, screenId, show);
	        return null;
	    }

	    
	    public List<String> getSeatsAvailability(Integer theatreId, Integer showId, Date date) {
	        Show show = searchShow(theatreId, showId);
	        if(show != null) {
	            show.getBookedSeats().get(date);
	        }
	        return null;
	    }

	    
	    public List<String> updateSeats(Integer theatreId, Integer showId, Date date,List<String> seats) {
	        Show show = searchShow(theatreId, showId);
	        //System.out.println(show);
	        show.getBookedSeats().get(date).addAll(seats);
	        return seats;
	    }

	    public List<String> cancelSeats(Integer theatreId, Integer showId,Date date,List<String> seats){
	        Show show = searchShow(theatreId, showId);
	        for(int i=0;i<seats.size();i++){
	            show.getBookedSeats().get(date).remove(seats.get(i));
	        }
	        return seats;
	    }

	    
	    public List<Show> getShowsInTheatre(Integer theatreId) {
	        List<Show> shows = new ArrayList<Show>();
	        List<Screen> screens = getScreensInTheatre(theatreId);
	        for(int i=0;i<screens.size();i++){
	            shows.addAll(screens.get(i).getShows());
	        }
	        return shows;
	    }

	    
	    public List<Show> getShowsInScreen(Integer theatreId, Integer screenId) {
	        List<Show> shows = new ArrayList<Show>();
	        for(int i=0;i<searchTheatre(theatreId).getScreens().size();i++){
	            if(searchTheatre(theatreId).getScreens().get(i).getScreenId().equals(screenId)){
	                shows.addAll(searchTheatre(theatreId).getScreens().get(i).getShows());
	                return shows;
	            }
	        }
	        return null;
	    }
	}


