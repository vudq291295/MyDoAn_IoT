package com.dqv.mqtt;

import com.dqv.mqtt.prometheus.PromMetricsExporter;
import com.dqv.mqtt.rest.RestApiVerticle;
import com.hazelcast.config.*;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.cli.CLI;
import io.vertx.core.cli.CLIException;
import io.vertx.core.cli.CommandLine;
import io.vertx.core.cli.Option;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.core.metrics.MetricsOptions;
import io.vertx.core.spi.cluster.ClusterManager;
import io.vertx.ext.dropwizard.DropwizardMetricsOptions;
import io.vertx.spi.cluster.hazelcast.HazelcastClusterManager;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Giovanni Baleani on 13/11/2015.
 */
public class Main {

    private static Logger logger = LoggerFactory.getLogger(Main.class);
    
    public static void main(String[] args) {
        start(args);
    }

    static CommandLine cli(String[] args) {
        CLI cli = CLI.create("java -jar <mqtt-broker>-fat.jar")
                .setSummary("A vert.x MQTT Broker")
                .addOption(new Option()
                                .setLongName("conf")
                                .setShortName("c")
                                .setDescription("vert.x config file (in json format)")
                                .setRequired(true)
                )
                .addOption(new Option()
                                .setLongName("hazelcast-conf")
                                .setShortName("hc")
                                .setDescription("vert.x hazelcast configuration file")
                                .setRequired(false)
                )
                .addOption(new Option()
                                .setLongName("hazelcast-host")
                                .setShortName("hh")
                                .setDescription("vert.x hazelcast ip address of this node (es. -hh 10.0.0.1)")
                                .setRequired(false)
                )
                .addOption(new Option()
                                .setLongName("hazelcast-members")
                                .setShortName("hm")
                                .setDescription("vert.x hazelcast list of tcp-ip members to add (es. -hm 10.0.0.1 10.0.0.2 10.0.0.3)")
                                .setRequired(false)
                                .setMultiValued(true)
                )
                ;

        // parsing
        CommandLine commandLine = null;
        try {
            List<String> userCommandLineArguments = Arrays.asList(args);
            commandLine = cli.parse(userCommandLineArguments);
        } catch(CLIException e) {
            // usage
            StringBuilder builder = new StringBuilder();
            cli.usage(builder);
            System.out.println(builder.toString());
//            throw e;
        }
        return commandLine;
    }

    public static void start(String[] args) {
        CommandLine commandLine = cli(args);
        if(commandLine == null)
            System.exit(-1);

        String confFilePath = commandLine.getOptionValue("c");
        logger.info("Startd Main, confFilePath: " + confFilePath);
        System.out.println("Startd Main, confFilePath: " + confFilePath);
        String hazelcastConfFilePath = commandLine.getOptionValue("hc");
        logger.info("Startd Main, hazelcastConfFilePath: " + hazelcastConfFilePath);
        String clusterHost = commandLine.getOptionValue("hh");
        logger.info("Startd Main, clusterHost: " + clusterHost);
        List<String> hazelcastMembers = commandLine.getOptionValues("hm");
        logger.info("Startd Main, hazelcastMembers: " + hazelcastMembers);
        DeploymentOptions deploymentOptions = new DeploymentOptions();
        if(confFilePath!=null) {
            try {
                String json = FileUtils.readFileToString(new File(confFilePath), "UTF-8");
                JsonObject config = new JsonObject(json);
                deploymentOptions.setConfig(config);
            } catch(IOException e) {
                logger.fatal(e.getMessage(),e);
            }
        }


        // use Vert.x CLI per gestire i parametri da riga di comando
        if(hazelcastConfFilePath!=null) {
            try {
                Config hazelcastConfig = new FileSystemXmlConfig(hazelcastConfFilePath);
                if(hazelcastMembers!=null) {
                    NetworkConfig network = hazelcastConfig.getNetworkConfig();
                    JoinConfig join = network.getJoin();
                    join.getMulticastConfig().setEnabled(false);
                    TcpIpConfig tcpIp = join.getTcpIpConfig();
                    for (String member : hazelcastMembers) {
                        tcpIp.addMember(member);
                    }
                    tcpIp.setEnabled(true);
                }

                ClusterManager mgr = new HazelcastClusterManager(hazelcastConfig);

                VertxOptions options = new VertxOptions().setClusterManager(mgr).setClustered(true);
                if(clusterHost != null) {
                    options.setClusterHost(clusterHost);

                    NetworkConfig network = hazelcastConfig.getNetworkConfig();
                    InterfacesConfig interfaces = network.getInterfaces();
                    interfaces.setEnabled(true);
                    interfaces.addInterface(clusterHost);
                }

                logger.info("Hazelcast public address: " +
                        hazelcastConfig.getNetworkConfig().getPublicAddress());
                logger.info("Hazelcast tcp-ip members: " +
                        hazelcastConfig.getNetworkConfig().getJoin().getTcpIpConfig().getMembers());
                logger.info("Hazelcast port: " +
                        hazelcastConfig.getNetworkConfig().getPort());
                logger.info("Hazelcast poutbound ports: " +
                        hazelcastConfig.getNetworkConfig().getOutboundPorts());
                logger.info("Hazelcast interfaces: " +
                        hazelcastConfig.getNetworkConfig().getInterfaces());
                logger.info("Hazelcast network config: " +
                        hazelcastConfig.getNetworkConfig().toString());

                options.setMetricsOptions(new DropwizardMetricsOptions()
                        .setEnabled(true)
                        .setJmxEnabled(true)
                );
                Vertx.clusteredVertx(options, res -> {
                    if (res.succeeded()) {
                        Vertx vertx = res.result();
                        vertx.deployVerticle(MQTTBroker.class.getName(), deploymentOptions);
                        vertx.deployVerticle(RestApiVerticle.class.getName(), deploymentOptions);
                        vertx.deployVerticle(PromMetricsExporter.class.getName(), deploymentOptions);
                    } else {
                        // failed!
                        logger.fatal(res.cause().getMessage(), res.cause());
                    }
                });
            } catch (FileNotFoundException e) {
                logger.fatal(e.getMessage(), e);
            }
        } else {
            VertxOptions options = new VertxOptions();
            options.setMetricsOptions(new DropwizardMetricsOptions()
                    .setEnabled(true)
                    .setJmxEnabled(true)
            );

            Vertx vertx = Vertx.vertx(options);
            vertx.deployVerticle(MQTTBroker.class.getName(), deploymentOptions);
            vertx.deployVerticle(RestApiVerticle.class.getName(), deploymentOptions);
            vertx.deployVerticle(PromMetricsExporter.class.getName(), deploymentOptions);
        }


    }


    public static void stop(String[] args) {
        System.exit(0);
    }

}
