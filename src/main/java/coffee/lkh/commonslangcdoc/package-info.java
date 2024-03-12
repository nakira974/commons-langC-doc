/**
 * This package is used to run an API that exposes a Doxygen documentation.
 * It, starts by the {@link coffee.lkh.commonslangcdoc.beans.CMakeBuildBean} that :
 * <ol>
 *     <li>clone the target repository</li>
 *     <li>Build the target repository using cmake MingGW Makefiles generator for windows and Unix Makefiles for other systems</li>
 *     <li>Publishes the doc into the temp directory</li>
 * </ol>
 *
 *
 *
 * @author Maxime Loukhal
 * @version 1.0
 * @since 2024-03-11
 */
package coffee.lkh.commonslangcdoc;
