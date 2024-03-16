package org.coolpot;

import joptsimple.*;
import org.coolpot.util.MetaConfig;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        OptionParser parser = new OptionParser();
        parser.acceptsAll(asList("?","h","help"),"Print helper message.");
        parser.acceptsAll(asList("v","version"),"Print compiler version.");
        parser.acceptsAll(asList("out","o"),"Set output file name").withRequiredArg()
                .ofType(String.class)
                .describedAs("filename");

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

        List<String> files = new ArrayList<>();
        for(Object optionSpec: set.nonOptionArguments()){
            if(optionSpec instanceof String){
                files.add((String) optionSpec);
            }
        }

        CompilerManager.compilers(files);
    }

    private static void printHelperInfo(Collection<OptionSpec<?>> collection){
        System.out.println("Usage: "+MetaConfig.name+" [options] file...\nOptions:");
        System.out.println("""
                -?, -h, --help             Print helper message.
                -v, --version              Print compiler version.
                -o, --out      <filename>  Set output file name.
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