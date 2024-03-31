package org.coolpot;

import joptsimple.*;
import org.coolpot.util.MetaConfig;

import java.io.File;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        OptionParser parser = new OptionParser();
        parser.acceptsAll(asList("?","h","help"),"Print helper message.");
        parser.acceptsAll(asList("v","version"),"Print compiler version.");
        parser.acceptsAll(asList("out","o"),"Set output file name").withRequiredArg()
                .ofType(String.class)
                .describedAs("filename");
        parser.acceptsAll(asList("lib","library","l"),"Set native library path.").withRequiredArg()
                .ofType(File.class)
                .defaultsTo(new File("lib"))
                .describedAs("dir_name");
        parser.acceptsAll(asList("ns","nosfn"),"Disable sfn command.");
        parser.acceptsAll(asList("r","runtime"),"Enable script runtime.");
        parser.acceptsAll(asList("nl","nostd"),"Disable compile library script.");
        parser.acceptsAll(asList("d","debug"),"Enable compiler debug model.");
        parser.acceptsAll(asList("o2","O2"),"Enable compiler debug model.");

        OptionSet set = parser.parse(args);

        if(args.length == 0||set.has("help")){
            Map<String,OptionSpec<?>> maps = parser.recognizedOptions();
            printHelperInfo(maps.values());
            System.exit(0);
        }

        if(set.has("version")){
            System.out.println(MetaConfig.version_info);
            System.exit(0);
        }

        if(set.has("nosfn")){
            MetaConfig.disableSFN = true;
        }

        if(set.has("nostd")){
            MetaConfig.disableSTD = true;
        }

        if(set.has("runtime")){
            MetaConfig.enableRuntime = true;
        }

        if(set.has("debug")){
            MetaConfig.isDebug = true;
        }

        if(set.has("o2")){
            MetaConfig.isO2 = true;
        }

        List<String> files = new ArrayList<>();
        for(Object optionSpec: set.nonOptionArguments()){
            if(optionSpec instanceof String){
                files.add((String) optionSpec);
            }
        }

        CompilerManager.compilers(set,files);
    }

    private static void printHelperInfo(Collection<OptionSpec<?>> collection){
        System.out.println("Usage: "+MetaConfig.name+" [options] file...\nOptions:");
        System.out.println("""
                -?, -h, --help             Print helper message.
                -v, --version              Print compiler version.
                -o, --out      <filename>  Set output file name.
                --ns, --nosfn              Disable sfn command.
                -r, --runtime              Enable script runtime.
                --nl, --nostd              Disable compile library script.
                -d, --debug                Enable compiler debug model.
                """);
        /*
        List<String> buffer = new ArrayList<>();
        for(OptionSpec<?> spec : collection){
            if(spec instanceof NonOptionArgumentSpec<?>) continue;
            if(spec instanceof OptionSpecBuilder builder){
                if(buffer.containsAll(builder.options())) continue;
                buffer.addAll(builder.options());

                for(String opt : builder.options())
                    System.out.print((opt.length() > 1 ? "--"+opt : "-"+opt)+", ");

                System.out.println("  "+ builder.description());
            }else if(spec instanceof ArgumentAcceptingOptionSpec<?> optionSpec){
                if(buffer.containsAll(optionSpec.options())) continue;
                buffer.addAll(optionSpec.options());
            }
        }
         */
    }

    private static List<String> asList(String... params) {
        return Arrays.asList(params);
    }
}