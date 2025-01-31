package org.embulk.input.example;

import java.util.List;
import org.embulk.config.ConfigDiff;
import org.embulk.config.ConfigSource;
import org.embulk.config.TaskReport;
import org.embulk.config.TaskSource;
import org.embulk.spi.InputPlugin;
import org.embulk.spi.PageOutput;
import org.embulk.spi.Schema;
import org.embulk.util.config.ConfigMapper;
import org.embulk.util.config.ConfigMapperFactory;
import org.embulk.util.config.TaskMapper;

@SuppressWarnings("unused")
public class ExampleInputPlugin implements InputPlugin {
  private static final ConfigMapperFactory CONFIG_MAPPER_FACTORY =
      ConfigMapperFactory.builder().addDefaultModules().build();

  @Override
  public ConfigDiff transaction(ConfigSource config, InputPlugin.Control control) {
    ConfigMapper configMapper = CONFIG_MAPPER_FACTORY.createConfigMapper();
    PluginTask task = configMapper.map(config, PluginTask.class);
    Schema schema = task.getColumns().toSchema();
    int taskCount = 1; // number of run() method calls
    return resume(task.toTaskSource(), schema, taskCount, control);
  }

  @Override
  public ConfigDiff resume(
      TaskSource taskSource, Schema schema, int taskCount, InputPlugin.Control control) {
    control.run(taskSource, schema, taskCount);
    return CONFIG_MAPPER_FACTORY.newConfigDiff();
  }

  @Override
  public void cleanup(
      TaskSource taskSource, Schema schema, int taskCount, List<TaskReport> successTaskReports) {}

  @Override
  public TaskReport run(TaskSource taskSource, Schema schema, int taskIndex, PageOutput output) {
    TaskMapper taskMapper = CONFIG_MAPPER_FACTORY.createTaskMapper();
    PluginTask task = taskMapper.map(taskSource, PluginTask.class);
    throw new UnsupportedOperationException("ExampleInputPlugin.run method is not implemented yet");
  }

  @Override
  public ConfigDiff guess(ConfigSource config) {
    return CONFIG_MAPPER_FACTORY.newConfigDiff();
  }
}
