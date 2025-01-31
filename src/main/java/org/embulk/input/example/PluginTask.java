package org.embulk.input.example;

import java.util.Optional;
import org.embulk.util.config.Config;
import org.embulk.util.config.ConfigDefault;
import org.embulk.util.config.Task;
import org.embulk.util.config.units.SchemaConfig;

@SuppressWarnings("unused")
public interface PluginTask extends Task {
  @Config("option1")
  int getOption1();

  @Config("option2")
  @ConfigDefault("\"myvalue\"")
  String getOption2();

  @Config("option3")
  @ConfigDefault("null")
  Optional<String> getOption3();

  @Config("columns")
  SchemaConfig getColumns();
}
