package cl.makinolas.atk.actors;

public abstract class Monsters extends AnimatedActor {
  
  public abstract void damage(int damage, Attacks inflictor);
  
  @Override
  public boolean isMonster(){
    return true;
  }
}

