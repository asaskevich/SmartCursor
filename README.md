SmartCursor
===========

Useful Minecraft mod with cursor utilities with own API.
Full information on [mods.io](https://mods.io/mods/1089-smartcursor)

### API Tutorial:
#### Step 1. 
At first step you should get API sources here: https://db.tt/W8xJ3fhH
#### Step 2.
Then you should to create new empty mod for MC 1.7.10 or MC 1.7.2.
#### Step 3. 
Specify dependencies for mod:
```java
Mod(modid = ..., dependencies="required-after:SmartCursor")
```
#### Step 4. 
Create new empty class that implements one of:

* IEntityProcessor
* IBlockProcessor
* IDropProcessor
* IPlayerProcessor

#### Step 5. 
Implement all necessary methods. Example:
```java
package example.mod;

import java.util.List;
import net.minecraft.entity.Entity;
import com.asaskevich.smartcursor.api.IEntityProcessor;

public class ExampleModule implements IEntityProcessor {
  @Override
  public String getModuleName() {
    return "Example mod";
  }
  @Override
  public String getAuthor() {
    return "user";
  }
  @Override
  public void process(List list, Entity entity) {
    list.add("Some text");
  }
}
```
#### Step 6. 
Connect your module to SmartCursor core:
```java
import com.asaskevich.smartcursor.api.ModuleConnector;
...
ModuleConnector.connectModule(new ExampleModule());
```

#### Step 7. 
Build and test your mod!
