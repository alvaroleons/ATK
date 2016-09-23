package cl.makinolas.atk.actors.enemies;

import java.util.HashMap;

import cl.makinolas.atk.actors.Hero;
import cl.makinolas.atk.actors.friend.AbstractFriend;
import cl.makinolas.atk.actors.friend.Bagon;
import cl.makinolas.atk.actors.friend.Beldum;
import cl.makinolas.atk.actors.friend.Caterpie;
import cl.makinolas.atk.actors.friend.Charmander;
import cl.makinolas.atk.actors.friend.Cubone;
import cl.makinolas.atk.actors.friend.Eevee;
import cl.makinolas.atk.actors.friend.Friend;
import cl.makinolas.atk.actors.friend.Gastly;
import cl.makinolas.atk.actors.friend.Geodude;
import cl.makinolas.atk.actors.friend.Gible;
import cl.makinolas.atk.actors.friend.Igglybuff;
import cl.makinolas.atk.actors.friend.Kangaskhan;
import cl.makinolas.atk.actors.friend.Magikarp;
import cl.makinolas.atk.actors.friend.Magnemite;
import cl.makinolas.atk.actors.friend.Mareep;
import cl.makinolas.atk.actors.friend.Pichu;
import cl.makinolas.atk.actors.friend.Rotom;
import cl.makinolas.atk.actors.friend.Sandshrew;
import cl.makinolas.atk.actors.friend.Scyther;
import cl.makinolas.atk.actors.friend.Shinx;
import cl.makinolas.atk.actors.friend.Skorupi;
import cl.makinolas.atk.actors.friend.Snivy;
import cl.makinolas.atk.actors.friend.Surskit;
import cl.makinolas.atk.actors.friend.Totodile;
import cl.makinolas.atk.actors.friend.Vulpix;
import cl.makinolas.atk.actors.friend.Weedle;
import cl.makinolas.atk.actors.friend.Zubat;

public class MonsterFactory {
  
  public static MonsterFactory monsterFactory = new MonsterFactory();
  private HashMap<String, EnemyFunction> map;
  
  private MonsterFactory(){
    map = new HashMap<String, EnemyFunction>();
    map.put("charmander", EnemyFunction.Charmander);
    map.put("charmeleon", EnemyFunction.Charmeleon);
    map.put("charizard", EnemyFunction.Charizard);
    map.put("caterpie", EnemyFunction.Caterpie);
    map.put("metapod", EnemyFunction.Metapod);
    map.put("butterfree", EnemyFunction.Butterfree);
    map.put("weedle", EnemyFunction.Weedle);
    map.put("kakuna", EnemyFunction.Kakuna);
    map.put("beedrill", EnemyFunction.Beedrill);
    map.put("pikachu", EnemyFunction.Pikachu);
    map.put("raichu", EnemyFunction.Raichu);
    map.put("sandshrew", EnemyFunction.Sandshrew);
    map.put("sandslash", EnemyFunction.Sandslash);
    map.put("vulpix", EnemyFunction.Vulpix);
    map.put("ninetales", EnemyFunction.Ninetales);
    map.put("jigglypuff", EnemyFunction.Jigglypuff);
    map.put("wigglytuff", EnemyFunction.Wigglytuff);
    map.put("zubat", EnemyFunction.Zubat);
    map.put("golbat", EnemyFunction.Golbat);
    map.put("geodude", EnemyFunction.Geodude);
    map.put("graveler", EnemyFunction.Graveler);
    map.put("golem", EnemyFunction.Golem);
    map.put("magnemite", EnemyFunction.Magnemite);
    map.put("magneton", EnemyFunction.Magneton);
    map.put("gastly", EnemyFunction.Gastly);
    map.put("haunter", EnemyFunction.Haunter);
    map.put("gengar", EnemyFunction.Gengar);
    map.put("cubone", EnemyFunction.Cubone);
    map.put("marowak", EnemyFunction.Marowak);
    map.put("kangaskhan", EnemyFunction.Kangaskhan);
    map.put("scyther", EnemyFunction.Scyther);
    map.put("magikarp", EnemyFunction.Magikarp);
    map.put("gyarados", EnemyFunction.Gyarados);
    map.put("eevee", EnemyFunction.Eevee);
    map.put("totodile", EnemyFunction.Totodile);
    map.put("croconaw", EnemyFunction.Croconaw);
    map.put("feraligatr", EnemyFunction.Feraligatr);
    map.put("crobat", EnemyFunction.Crobat);
    map.put("pichu", EnemyFunction.Pichu);
    map.put("igglybuff", EnemyFunction.Igglybuff);
    map.put("mareep", EnemyFunction.Mareep);
    map.put("flaaffy", EnemyFunction.Flaaffy);
    map.put("ampharos", EnemyFunction.Ampharos);
    map.put("scizor", EnemyFunction.Scizor);
    map.put("surskit", EnemyFunction.Surskit);
    map.put("masquerain", EnemyFunction.Masquerain);
    map.put("bagon", EnemyFunction.Bagon);
    map.put("shelgon", EnemyFunction.Shelgon);
    map.put("salamence", EnemyFunction.Salamence);
    map.put("beldum", EnemyFunction.Beldum);
    map.put("metang", EnemyFunction.Metang);
    map.put("metagross", EnemyFunction.Metagross);
    map.put("shinx", EnemyFunction.Shinx);
    map.put("luxio", EnemyFunction.Luxio);
    map.put("luxray", EnemyFunction.Luxray);
    map.put("gible", EnemyFunction.Gible);
    map.put("gabite", EnemyFunction.Gabite);
    map.put("garchomp", EnemyFunction.Garchomp);
    map.put("skorupi", EnemyFunction.Skorupi);
    map.put("drapion", EnemyFunction.Drapion);
    map.put("magnezone", EnemyFunction.Magnezone);
    map.put("rotom", EnemyFunction.Rotom);
    map.put("snivy", EnemyFunction.Snivy);
    map.put("servine", EnemyFunction.Servine);
    map.put("serperior", EnemyFunction.Serperior);
  }
  
  public static MonsterFactory getInstance(){
    return monsterFactory;
  }
  
  public enum EnemyFunction{
    
    Charmander{
      @Override
      public Friend giveFriend(int level) {
        return new Charmander(level);
      }
    },
    Charmeleon{
      @Override
      public Friend giveFriend(int level) {
        return getEvolution(new Charmander(level), 1);
      }
    },
    Charizard{
      @Override
      public Friend giveFriend(int level) {
        return getEvolution(new Charmander(level), 2);
      }
    },
    Caterpie{
      @Override
      public Friend giveFriend(int level) {
        return new Caterpie(level);
      }
    },
    Metapod{
      @Override
      public Friend giveFriend(int level) {
        return getEvolution(new Caterpie(level), 1);
      }
    },
    Butterfree{
      @Override
      public Friend giveFriend(int level) {
        return getEvolution(new Caterpie(level), 2);
      }
    },
    Weedle{
      @Override
      public Friend giveFriend(int level) {
        return new Weedle(level);
      }
    },
    Kakuna{
      @Override
      public Friend giveFriend(int level) {
        return getEvolution(new Weedle(level), 1);
      }
    },
    Beedrill{
      @Override
      public Friend giveFriend(int level) {
        return getEvolution(new Weedle(level), 2);
      }
    },
    Pikachu{
      @Override
      public Friend giveFriend(int level) {
        return getEvolution(new Pichu(level), 1); 
      }
    },
    Raichu{
      @Override
      public Friend giveFriend(int level) {
        return getEvolution(new Pichu(level), 2); 
      }
    },
    Sandshrew{
      @Override
      public Friend giveFriend(int level) {
        return new Sandshrew(level);
      }
    },
    Sandslash{
      @Override
      public Friend giveFriend(int level) {
        return getEvolution(new Sandshrew(level), 1); 
      }
    },
    Vulpix{
      @Override
      public Friend giveFriend(int level) {
        return new Vulpix(level);
      }
    },
    Ninetales{
      @Override
      public Friend giveFriend(int level) {
        return getEvolution(new Vulpix(level), 1);
      }
    },
    Jigglypuff{
      @Override
      public Friend giveFriend(int level) {
        return getEvolution(new Igglybuff(level), 1);
      }
    },
    Wigglytuff{
      @Override
      public Friend giveFriend(int level) {
        return getEvolution(new Igglybuff(level), 2);
      }
    },
    Zubat{
      @Override
      public Friend giveFriend(int level) {
        return new Zubat(level);
      }
    },
    Golbat{
      @Override
      public Friend giveFriend(int level) {
        return getEvolution(new Zubat(level), 1);
      }
    },
    Geodude{
      @Override
      public Friend giveFriend(int level) {
        return new Geodude(level);
      }
    },
    Graveler{
      @Override
      public Friend giveFriend(int level) {
        return getEvolution(new Geodude(level), 1);
      }
    },
    Golem{
      @Override
      public Friend giveFriend(int level) {
        return getEvolution(new Geodude(level), 2);
      }
    },
    Magnemite{
      @Override
      public Friend giveFriend(int level) {
        return new Magnemite(level);
      }
    },
    Magneton{
      @Override
      public Friend giveFriend(int level) {
        return getEvolution(new Magnemite(level), 1);
      }
    },
    Gastly{
      @Override
      public Friend giveFriend(int level) {
        return (new Gastly(level));
      }      
    },
    Haunter{
      @Override
      public Friend giveFriend(int level) {
        return getEvolution(new Gastly(level), 1);
      }      
    },
    Gengar{
      @Override
      public Friend giveFriend(int level) {
        return getEvolution(new Gastly(level), 2);
      }      
    },
    Cubone{
      @Override
      public Friend giveFriend(int level) {
        return new Cubone(level);
      }      
    },
    Marowak{
      @Override
      public Friend giveFriend(int level) {
        return getEvolution(new Cubone(level), 1);
      }      
    },
    Kangaskhan{
      @Override
      public Friend giveFriend(int level) {
        return new Kangaskhan(level);
      }      
    },
    Scyther{
      @Override
      public Friend giveFriend(int level) {
        return new Scyther(level);
      }
    },
    Magikarp{
      @Override
      public Friend giveFriend(int level) {
        return new Magikarp(level);
      }
    },
    Gyarados{
      @Override
      public Friend giveFriend(int level) {
        return getEvolution(new Magikarp(level), 1);
      }
    },
    Eevee{
      @Override
      public Friend giveFriend(int level) {
        return new Eevee(level);
      }
    },
    Totodile{
      @Override
      public Friend giveFriend(int level) {
        return new Totodile(level);
      }
    },
    Croconaw{
      @Override
      public Friend giveFriend(int level) {
        return getEvolution(new Totodile(level), 1);
      }
    },
    Feraligatr{
      @Override
      public Friend giveFriend(int level) {
        return getEvolution(new Totodile(level), 2);
      }
    },
    Crobat{
      @Override
      public Friend giveFriend(int level) {
        return getEvolution(new Zubat(level), 2);
      }
    },
    Pichu{
      @Override
      public Friend giveFriend(int level) {
        return new Pichu(level);
      }
    },
    Igglybuff{
      @Override
      public Friend giveFriend(int level) {
        return new Igglybuff(level);
      }
    },
    Mareep{
      @Override
      public Friend giveFriend(int level) {
        return new Mareep(level);
      }
    },
    Flaaffy{
      @Override
      public Friend giveFriend(int level) {
        return getEvolution(new Mareep(level), 1);
      }
    },
    Ampharos{
      @Override
      public Friend giveFriend(int level) {
        return getEvolution(new Mareep(level), 2);
      }
    },
    Scizor{
      @Override
      public Friend giveFriend(int level) {
        return getEvolution(new Scyther(level), 1);
      }
    },
    Surskit{
      @Override
      public Friend giveFriend(int level) {
        return new Surskit(level);
      }
    },
    Masquerain{
      @Override
      public Friend giveFriend(int level) {
        return getEvolution(new Surskit(level), 1);
      }
    },
    Bagon{
      @Override
      public Friend giveFriend(int level) {
        return new Bagon(level);
      }
    },
    Shelgon{
      @Override
      public Friend giveFriend(int level) {
        return getEvolution(new Bagon(level), 1);
      }
    },
    Salamence{
      @Override
      public Friend giveFriend(int level) {
        return getEvolution(new Bagon(level), 2);
      }
    },
    Beldum{
      @Override
      public Friend giveFriend(int level) {
        return new Beldum(level);
      }
    },
    Metang{
      @Override
      public Friend giveFriend(int level) {
        return getEvolution(new Beldum(level), 1);
      }
    },
    Metagross{
      @Override
      public Friend giveFriend(int level) {
        return getEvolution(new Beldum(level), 2);
      }
    },
    Shinx{
      @Override
      public Friend giveFriend(int level) {
        return new Shinx(level);
      }
    },
    Luxio{
      @Override
      public Friend giveFriend(int level) {
        return getEvolution(new Shinx(level), 1);
      }
    },
    Luxray{
      @Override
      public Friend giveFriend(int level) {
        return getEvolution(new Shinx(level), 2);
      }
    },
    Gible{
      @Override
      public Friend giveFriend(int level) {
        return new Gible(level);
      }
    },
    Gabite{
      @Override
      public Friend giveFriend(int level) {
        return getEvolution(new Gible(level), 1);
      }
    },
    Garchomp{
      @Override
      public Friend giveFriend(int level) {
        return getEvolution(new Gible(level), 2);
      }
    },
    Skorupi{
      @Override
      public Friend giveFriend(int level) {
        return new Skorupi(level);
      }
    },
    Drapion{
      @Override
      public Friend giveFriend(int level) {
        return getEvolution(new Skorupi(level), 1);
      }
    },
    Magnezone{
      @Override
      public Friend giveFriend(int level) {
        return getEvolution(new Magnemite(level), 2);
      }
    },
    Rotom{
      @Override
      public Friend giveFriend(int level) {
        return new Rotom(level);
      }
    },
    Snivy{
      @Override
      public Friend giveFriend(int level) {
        return new Snivy(level);
      }
    },
    Servine{
      @Override
      public Friend giveFriend(int level) {
        return getEvolution(new Snivy(level), 1);
      }
    },
    Serperior{
      @Override
      public Friend giveFriend(int level) {
        return getEvolution(new Snivy(level), 2);
      }
    };
    
    protected Friend getEvolution(AbstractFriend friend, int numberOfEvolution){
      friend.forceEvolve(numberOfEvolution);
      return friend;
    }
    
    protected Enemy giveEnemy(int level, int positionX, int positionY){
      return giveFriend(level).returnEnemy(Hero.getInstance().getMyWorld(), positionX, positionY);
    }
    
    protected Enemy giveStayAndShootEnemy(int level, int positionX, int positionY) {
      return giveFriend(level).returnStayAndShootEnemy(Hero.getInstance().getMyWorld(), positionX, positionY);
    }
    
    protected Enemy giveFlyWaveAndDropEnemy(int level, int positionX, int positionY) {
      return giveFriend(level).returnFlyWaveAndDropEnemy(Hero.getInstance().getMyWorld(), positionX, positionY);
    }
    
    protected Enemy giveJumperEnemy(int level, int positionX, int positionY) {
      return giveFriend(level).returnJumperEnemy(Hero.getInstance().getMyWorld(), positionX, positionY);
    }
    
    protected Enemy giveFollowerEnemy(int level, int positionX, int positionY) {
      return giveFriend(level).returnFollowerEnemy(Hero.getInstance().getMyWorld(), positionX, positionY);
    }
    
    protected Enemy givePhysicalEnemy(int level, int position){
      return giveFriend(level).returnPhysicalEnemy(Hero.getInstance().getMyWorld(), position);
    }
    
    protected Enemy giveLongRangeEnemy(int level, int position){
      return giveFriend(level).returnLongRangeEnemy(Hero.getInstance().getMyWorld(), position);
    }
    
    public abstract Friend giveFriend(int level);


  }
  
  public Enemy giveClassicEnemy(String nameFriend, int level, int positionX, int positionY){
    return map.get(nameFriend.toLowerCase()).giveEnemy(level, positionX, positionY); 
  }
  
  public Enemy giveStayAndShootEnemy(String nameFriend, int level, int positionX, int positionY) {
    return map.get(nameFriend.toLowerCase()).giveStayAndShootEnemy(level, positionX, positionY); 
  }
  
  public Enemy giveFlyWaveAndDropEnemy(String nameFriend, int level, int positionX, int positionY) {
    return map.get(nameFriend.toLowerCase()).giveFlyWaveAndDropEnemy(level, positionX, positionY); 
  }
  
  public Enemy giveJumperEnemy(String nameFriend, int level, int positionX, int positionY) {
    return map.get(nameFriend.toLowerCase()).giveJumperEnemy(level, positionX, positionY); 
  }
  
  public Enemy giveFollowerEnemy(String nameFriend, int level, int positionX, int positionY) {
    return map.get(nameFriend.toLowerCase()).giveFollowerEnemy(level, positionX, positionY); 
  }
  
  public Enemy givePhysicalEnemy(String nameFriend, int level, int position){
    return map.get(nameFriend.toLowerCase()).givePhysicalEnemy(level, position); 
  }
  
  public Enemy giveRangedEnemy(String nameFriend, int level, int position){
    return map.get(nameFriend.toLowerCase()).giveLongRangeEnemy(level, position); 
  }
  
  public Friend getHeroFriend(String nameFriend, int level){
    return map.get(nameFriend.toLowerCase()).giveFriend(level);
  }





  
}
